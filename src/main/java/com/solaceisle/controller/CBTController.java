package com.solaceisle.controller;

import com.solaceisle.pojo.dto.CBTDTO;
import com.solaceisle.pojo.vo.CBTDetailVO;
import com.solaceisle.pojo.vo.CBTVO;
import com.solaceisle.result.Result;
import com.solaceisle.service.CBTService;
import io.github.imfangs.dify.client.exception.DifyApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cbt")
@RequiredArgsConstructor
public class CBTController {

    private final CBTService cbtService;

    @GetMapping
    public Result<List<CBTVO>> getCBTs() {
        return Result.success(cbtService.getCBTs());
    }

    @GetMapping("/{id}")
    public Result<List<CBTDetailVO>> getCBTDetail(@PathVariable Integer id) {
        return Result.success(cbtService.getCBTDetail(id));
    }

    @PostMapping(path = "/{id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter postCBT(@PathVariable Integer id, @RequestBody List<CBTDTO> answer)
            throws DifyApiException, IOException {
        return cbtService.postCBT(answer, id);
    }
}
