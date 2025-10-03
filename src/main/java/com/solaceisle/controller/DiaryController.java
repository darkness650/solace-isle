package com.solaceisle.controller;

import com.solaceisle.pojo.dto.DiaryDTO;
import com.solaceisle.pojo.vo.DiaryVO;
import com.solaceisle.result.Result;
import com.solaceisle.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping
    public Result<List<DiaryVO>> getMonthDiary(String date) {
        List<DiaryVO> diaries = diaryService.getMonthDiary(date);
        return Result.success(diaries);
    }
    @PostMapping
    public Result addDiary(@RequestBody DiaryDTO diaryDTO) {
        diaryService.addDiary(diaryDTO);
        return Result.success();
    }
    @PostMapping("/tags")
    public Result<List<String>> getTags(String text) {
        List<String> tags = diaryService.getTags(text);
        return Result.success(tags);
    }
}
