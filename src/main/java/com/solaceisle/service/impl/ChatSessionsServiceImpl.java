package com.solaceisle.service.impl;

import com.solaceisle.context.BaseContext;
import com.solaceisle.pojo.vo.ChatSessionMessagesVO;
import com.solaceisle.pojo.vo.ChatSessionsVO;
import com.solaceisle.service.ChatSessionsService;
import io.github.imfangs.dify.client.DifyChatflowClient;
import io.github.imfangs.dify.client.exception.DifyApiException;
import io.github.imfangs.dify.client.model.chat.Conversation;
import io.github.imfangs.dify.client.model.chat.ConversationListResponse;
import io.github.imfangs.dify.client.model.chat.MessageListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatSessionsServiceImpl implements ChatSessionsService {

    private final DifyChatflowClient chatPartnerClient;

    @Override
    public void renameSession(String id, String title) throws DifyApiException, IOException {
        chatPartnerClient.renameConversation(id, title, false, BaseContext.getCurrentId());
    }

    @Override
    public void deleteSession(String id) throws DifyApiException, IOException {
        chatPartnerClient.deleteConversation(id, BaseContext.getCurrentId());
    }

    @Override
    public ChatSessionMessagesVO getSessionMessages(String id, String firstId, Integer limit) throws DifyApiException, IOException {
        MessageListResponse messageListResponse = chatPartnerClient.getMessages(id, BaseContext.getCurrentId(), firstId, limit);
        var chatSessionMessagesVO = new ChatSessionMessagesVO();
        BeanUtils.copyProperties(messageListResponse, chatSessionMessagesVO);

        // 将List手动进行转换
        List<MessageListResponse.Message> data = messageListResponse.getData();
        for (MessageListResponse.Message message : data) {
            var messageVO = new ChatSessionMessagesVO.MessageVO();
            BeanUtils.copyProperties(message, messageVO);
            // 时间戳转为LocalDateTime
            long timestamp = message.getCreatedAt();
            messageVO.setDatetime(LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8)));
            chatSessionMessagesVO.getMessages().add(messageVO);
        }
        return chatSessionMessagesVO;
    }

    @Override
    public ChatSessionsVO getSessions(String lastId, Integer limit) throws DifyApiException, IOException {
        var conversationListResponse = chatPartnerClient.getConversations(BaseContext.getCurrentId(), lastId, limit, null);
        var chatSessionsVO = new ChatSessionsVO();
        BeanUtils.copyProperties(conversationListResponse, chatSessionsVO);

        // 将List手动进行转换
        List<Conversation> data = conversationListResponse.getData();
        for (Conversation conversation : data) {
            var sessionVO = new ChatSessionsVO.SessionVO();
            sessionVO.setId(conversation.getId());
            sessionVO.setTitle(conversation.getName());
            // 时间戳转为LocalDateTime
            long timestamp = conversation.getUpdatedAt();
            sessionVO.setDatetime(LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8)));
            chatSessionsVO.getSessions().add(sessionVO);
        }

        return chatSessionsVO;
    }
}
