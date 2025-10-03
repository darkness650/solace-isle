package com.solaceisle.service.impl;

import com.solaceisle.context.BaseContext;
import com.solaceisle.pojo.vo.chat.*;
import com.solaceisle.service.ChatService;
import io.github.imfangs.dify.client.DifyChatflowClient;
import io.github.imfangs.dify.client.callback.ChatflowStreamCallback;
import io.github.imfangs.dify.client.event.*;
import io.github.imfangs.dify.client.exception.DifyApiException;
import io.github.imfangs.dify.client.model.chat.ChatMessage;
import io.github.imfangs.dify.client.model.chat.Conversation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final DifyChatflowClient chatPartnerClient;

    @Override
    public SseEmitter chat(String id, String query) throws DifyApiException, IOException {
        ChatMessage chatMessage = ChatMessage.builder()
                .query(query).user(id).build();

        SseEmitter emitter = new SseEmitter();

        chatPartnerClient.sendChatMessageStream(chatMessage, new ChatflowStreamCallback() {
            @SneakyThrows
            @Override
            public void onWorkflowStarted(WorkflowStartedEvent event) {
                var workflowStartedVO = new WorkflowStartedVO();
                BeanUtils.copyProperties(event, workflowStartedVO);
                emitter.send(workflowStartedVO);
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
            }

            @SneakyThrows
            @Override
            public void onNodeFinished(NodeFinishedEvent event) {
                var nodeFinishedVO = new NodeFinishedVO();
                BeanUtils.copyProperties(event, nodeFinishedVO);
                emitter.send(nodeFinishedVO);
            }

            @SneakyThrows
            @Override
            public void onWorkflowFinished(WorkflowFinishedEvent event) {
                var workflowFinishedVO = new WorkflowFinishedVO();
                BeanUtils.copyProperties(event, workflowFinishedVO);
                emitter.send(workflowFinishedVO);
            }

            @SneakyThrows
            @Override
            public void onMessage(MessageEvent event) {
                var messageVO = new MessageVO();
                BeanUtils.copyProperties(event, messageVO);
                emitter.send(messageVO);
            }

            @SneakyThrows
            @Override
            public void onMessageEnd(MessageEndEvent event) {
                var messageEndVO = new MessageEndVO();
                BeanUtils.copyProperties(event, messageEndVO);
                emitter.send(messageEndVO);
                emitter.complete();
            }

            @SneakyThrows
            @Override
            public void onError(ErrorEvent event) {
                var errorVO = new ErrorVO();
                BeanUtils.copyProperties(event, errorVO);
                emitter.send(errorVO);
                emitter.complete();
            }

            @SneakyThrows
            @Override
            public void onPing(PingEvent event) {
                var pingVO = new PingVO();
                BeanUtils.copyProperties(event, pingVO);
                emitter.send(pingVO);
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
}
