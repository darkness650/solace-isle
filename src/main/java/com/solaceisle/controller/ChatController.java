package com.solaceisle.controller;

import com.solaceisle.pojo.dto.ChatDTO;
import com.solaceisle.result.Result;
import com.solaceisle.service.ChatService;
import io.github.imfangs.dify.client.exception.DifyApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter chat(@RequestBody ChatDTO chatDTO) throws DifyApiException, IOException {
        return chatService.chat(chatDTO.getId(), chatDTO.getQuery());
    }

    @GetMapping("/title/{id}")
    public Result<String> generateTitle(@PathVariable String id) throws DifyApiException, IOException {
        String title = chatService.generateTitle(id);
        return Result.success(title);
    }

    @PostMapping("/{taskId}/stop")
    public Result<?> stopChat(@PathVariable String taskId) throws DifyApiException, IOException {
        chatService.stopChat(taskId);
        return Result.success();
    }

    @GetMapping
    public Result<List<String>> getIntroSuggestions()
            throws DifyApiException, IOException, ExecutionException, InterruptedException {
        List<String> suggestions = chatService.getIntroSuggestions();
        return Result.success(suggestions);
    }
}
