package com.solaceisle.controller;

import com.solaceisle.pojo.dto.RenameChatSessionDTO;
import com.solaceisle.pojo.vo.ChatSessionMessagesVO;
import com.solaceisle.pojo.vo.ChatSessionsVO;
import com.solaceisle.result.Result;
import com.solaceisle.service.ChatSessionsService;
import io.github.imfangs.dify.client.exception.DifyApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/chat/sessions")
@RequiredArgsConstructor
public class ChatSessionsController {

    private final ChatSessionsService chatSessionsService;

    @PostMapping("/{id}")
    public Result<?> renameSession(
            @PathVariable String id,
            @RequestBody RenameChatSessionDTO renameChatSessionDTO)
            throws DifyApiException, IOException {
        chatSessionsService.renameSession(id, renameChatSessionDTO.getTitle());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteSession(@PathVariable String id) throws DifyApiException, IOException {
        chatSessionsService.deleteSession(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<ChatSessionMessagesVO> getSessionMessages(
            @PathVariable String id,
            @RequestParam(required = false) String firstId,
            @RequestParam(required = false, defaultValue = "20") Integer limit)
            throws DifyApiException, IOException {
        ChatSessionMessagesVO chatSessionMessagesVO = chatSessionsService.getSessionMessages(id, firstId, limit);
        return Result.success(chatSessionMessagesVO);
    }

    @GetMapping("/list")
    public Result<ChatSessionsVO> getSessions(
            @RequestParam(required = false) String lastId,
            @RequestParam(required = false) Integer limit)
            throws DifyApiException, IOException {
        ChatSessionsVO chatSessionsVO = chatSessionsService.getSessions(lastId, limit);
        return Result.success(chatSessionsVO);
    }
}
