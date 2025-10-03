package com.solaceisle.controller;

import com.solaceisle.pojo.vo.DiaryVO;
import com.solaceisle.result.Result;
import com.solaceisle.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
