package com.solaceisle.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.solaceisle.constant.MessageConstant;
import com.solaceisle.context.BaseContext;
import com.solaceisle.exception.SuggestionGeneratorException;
import com.solaceisle.mapper.UserMapper;
import com.solaceisle.pojo.entity.User;
import com.solaceisle.pojo.vo.chat.*;
import com.solaceisle.service.ChatService;
import io.github.imfangs.dify.client.DifyChatClient;
import io.github.imfangs.dify.client.DifyChatflowClient;
import io.github.imfangs.dify.client.DifyWorkflowClient;
import io.github.imfangs.dify.client.callback.ChatStreamCallback;
import io.github.imfangs.dify.client.callback.ChatflowStreamCallback;
import io.github.imfangs.dify.client.enums.ResponseMode;
import io.github.imfangs.dify.client.event.*;
import io.github.imfangs.dify.client.exception.DifyApiException;
import io.github.imfangs.dify.client.model.chat.ChatMessage;
import io.github.imfangs.dify.client.model.chat.Conversation;
import io.github.imfangs.dify.client.model.workflow.WorkflowRunRequest;
import io.github.imfangs.dify.client.model.workflow.WorkflowRunResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final DifyChatflowClient chatPartnerClient;
    private final UserMapper userMapper;
    private final DifyWorkflowClient suggestionGeneratorClient;

    @Value("${solace.hotline}")
    private String hotline;

    @Override
    public SseEmitter chat(String id, String query) throws DifyApiException, IOException {
        // 获取用户的邮箱地址
        String studentId = BaseContext.getCurrentId();
        User user = userMapper.getUserProfile(studentId);
        String email = user.getEmail();

        // 构造需要发送的消息
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("email", email);
        inputs.put("id", studentId);
        inputs.put("hotline", hotline);

        ChatMessage chatMessage = ChatMessage.builder()
                .query(query)
                .user(BaseContext.getCurrentId())
                .inputs(inputs)
                .conversationId(id)
                .build();

        SseEmitter emitter = new SseEmitter(2 * 60 * 1000L);

        chatPartnerClient.sendChatMessageStream(chatMessage, new ChatflowStreamCallback() {
            @SneakyThrows
            @Override
            public void onWorkflowStarted(WorkflowStartedEvent event) {
                var workflowStartedVO = new WorkflowStartedVO();
                BeanUtils.copyProperties(event, workflowStartedVO);
                emitter.send(workflowStartedVO);
                log.info(event.getEvent());
            }

            @SneakyThrows
            @Override
            public void onNodeStarted(NodeStartedEvent event) {
                var nodeStartedVO = new NodeStartedVO();
                BeanUtils.copyProperties(event, nodeStartedVO);
                // 将Data字段进行转换
                var data = new NodeStartedVO.Data();
                BeanUtils.copyProperties(event.getData(), data);
                nodeStartedVO.setData(data);
                emitter.send(nodeStartedVO);
                log.info(event.getEvent());
            }

            @SneakyThrows
            @Override
            public void onNodeFinished(NodeFinishedEvent event) {
                var nodeFinishedVO = new NodeFinishedVO();
                BeanUtils.copyProperties(event, nodeFinishedVO);
                emitter.send(nodeFinishedVO);
                log.info(event.getEvent());
            }

            @SneakyThrows
            @Override
            public void onWorkflowFinished(WorkflowFinishedEvent event) {
                var workflowFinishedVO = new WorkflowFinishedVO();
                BeanUtils.copyProperties(event, workflowFinishedVO);
                emitter.send(workflowFinishedVO);
                log.info(event.getEvent());
            }

            @SneakyThrows
            @Override
            public void onMessage(MessageEvent event) {
                var messageVO = new MessageVO();
                BeanUtils.copyProperties(event, messageVO);
                emitter.send(messageVO);
                log.info(event.getEvent());
            }

            @SneakyThrows
            @Override
            public void onMessageEnd(MessageEndEvent event) {
                var messageEndVO = new MessageEndVO();
                BeanUtils.copyProperties(event, messageEndVO);
                emitter.send(messageEndVO);
                log.info("发送消息正常完成");
                emitter.complete();
                log.info(event.getEvent());
            }

            @SneakyThrows
            @Override
            public void onError(ErrorEvent event) {
                var errorVO = new ErrorVO();
                BeanUtils.copyProperties(event, errorVO);
                emitter.send(errorVO);
                log.info("发送消息异常完成");
                emitter.complete();
                log.info(event.getEvent());
            }

            @SneakyThrows
            @Override
            public void onPing(PingEvent event) {
                var pingVO = new PingVO();
                BeanUtils.copyProperties(event, pingVO);
                emitter.send(pingVO);
                log.info(event.getEvent());
            }
        });

        return emitter;
    }

    @Override
    public String generateTitle(String id) throws DifyApiException, IOException {
        Conversation conversation = chatPartnerClient.renameConversation(id, null, true, BaseContext.getCurrentId());
        return conversation.getName();
    }

    @Override
    public void stopChat(String taskId) throws DifyApiException, IOException {
        chatPartnerClient.stopChatMessage(taskId, BaseContext.getCurrentId());
    }

    @Override
    public List<String> getIntroSuggestions() throws DifyApiException, IOException, ExecutionException, InterruptedException {
        var studentId = BaseContext.getCurrentId();
        var request= WorkflowRunRequest.builder()
                .user(studentId)
                .inputs(Map.of("id",studentId))
                .responseMode(ResponseMode.BLOCKING)
                .build();

        WorkflowRunResponse response = suggestionGeneratorClient.runWorkflow(request);
        Object text = response.getData().getOutputs().getOrDefault("text", "[]");

        return JSON.parseArray((String)text, String.class);
    }
}


