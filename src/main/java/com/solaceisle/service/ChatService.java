package com.solaceisle.service;

import io.github.imfangs.dify.client.exception.DifyApiException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ChatService {

    SseEmitter chat(String id, String query) throws DifyApiException, IOException;

    String generateTitle(String id) throws DifyApiException, IOException;

    void stopChat(String taskId) throws DifyApiException, IOException;

    List<String> getIntroSuggestions() throws DifyApiException, IOException, ExecutionException, InterruptedException;
}
