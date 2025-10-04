package com.solaceisle.service;

import com.solaceisle.pojo.vo.ChatSessionMessagesVO;
import com.solaceisle.pojo.vo.ChatSessionsVO;
import io.github.imfangs.dify.client.exception.DifyApiException;

import java.io.IOException;

public interface ChatSessionsService {

    void renameSession(String id, String title) throws DifyApiException, IOException;

    void deleteSession(String id) throws DifyApiException, IOException;

    ChatSessionMessagesVO getSessionMessages(String id, String firstId, Integer limit) throws DifyApiException, IOException;

    ChatSessionsVO getSessions(String lastId, Integer limit) throws DifyApiException, IOException;
}
