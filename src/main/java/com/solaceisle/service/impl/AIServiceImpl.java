package com.solaceisle.service.impl;

import com.solaceisle.constant.MessageConstant;
import com.solaceisle.context.BaseContext;
import com.solaceisle.exception.IllegalDateFormatException;
import com.solaceisle.mapper.DiaryMapper;
import com.solaceisle.pojo.entity.Diary;
import com.solaceisle.pojo.vo.DiaryVO;
import com.solaceisle.service.AIService;
import com.solaceisle.service.DiaryService;
import com.solaceisle.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final EmailUtil emailUtil;
    private final DiaryService diaryService;

    @Value("${solace.hotline}")
    private String hotline;
    private static final String YEAR_MONTH_REGEX = "^\\d{4}-(0[1-9]|1[0-2])$";
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
