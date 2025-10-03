package com.solaceisle.service;

import com.solaceisle.pojo.vo.DiaryVO;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.List;

public interface DiaryService {

    @Tool(description = "获取某月的日记列表")
    List<DiaryVO> getMonthDiary(
            @ToolParam(description = "某年某月，格式为yyyy-MM") String yearMonth);

}

