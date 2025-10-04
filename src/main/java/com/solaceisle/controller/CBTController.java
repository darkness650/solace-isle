package com.solaceisle.controller;

import com.solaceisle.pojo.dto.CBTDTO;
import com.solaceisle.pojo.po.CBTDeatilVO;
import com.solaceisle.pojo.po.CBTVO;
import com.solaceisle.result.Result;
import com.solaceisle.service.CBTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/cbt")
@RequiredArgsConstructor
public class CBTController {
    private final CBTService cbtService;
    @GetMapping
    public Result<List<CBTVO>> getCBT() {
        return Result.success(cbtService.getCBT());
    }
    @GetMapping("/{id}")
    public Result<List<CBTDeatilVO>> getCBTDetail(@PathVariable Integer id) {
        return Result.success(cbtService.getCBTDetail(id));
    }
    @PostMapping("/{id}")
    public SseEmitter postCBT(@RequestBody List<CBTDTO> answer,@PathVariable Integer id) {
        return cbtService.postCBT(answer,id);
    }
}
