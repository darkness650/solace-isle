package com.solaceisle.service;

import com.solaceisle.pojo.vo.DiaryVO;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.List;

public interface DiaryService {

    List<DiaryVO> getMonthDiary(String yearMonth);

}

