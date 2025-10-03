package com.solaceisle.service.impl;

import com.solaceisle.pojo.vo.DiaryVO;
import com.solaceisle.service.AIService;
import com.solaceisle.service.DiaryService;
import com.solaceisle.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final EmailUtil emailUtil;
    private final DiaryService diaryService;

    @Value("${solace.hotline}")
    private String hotline;

    @Override
    public String getHotline() {
        return hotline;
    }

    @Override
    public void sendTextEmail(String to, String subject, String text) {
        emailUtil.sendTextEmail(to, subject, text);
    }

    @Override
    public List<DiaryVO> getMonthDiary(String yearMonth) {
        return diaryService.getMonthDiary(yearMonth);
    }
}
