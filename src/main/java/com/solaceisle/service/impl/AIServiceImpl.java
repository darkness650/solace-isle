package com.solaceisle.service.impl;

import com.alibaba.fastjson2.JSON;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private static final String YEAR_MONTH_REGEX = "^\\d{4}-(0[1-9]|1[0-2])$";

    private final EmailUtil emailUtil;

    private final DiaryMapper diaryMapper;

    @Override
    public void sendTextEmail(String to, String subject, String text) {
        emailUtil.sendTextEmail(to, subject, text);
    }

    @Override
    public List<DiaryVO> getMonthDiary(String id, String yearMonth) {
        if (yearMonth == null || !yearMonth.matches(YEAR_MONTH_REGEX)) {
            throw new IllegalDateFormatException(MessageConstant.DATE_FORMAT_ERROR);
        }
        LocalDate start = LocalDate.parse(yearMonth + "-01");
        LocalDate end = start.plusMonths(1);

        List<Diary> diaries = diaryMapper.findByStudentIdAndYearMonth(id, start, end);

        List<DiaryVO> diaryVOs = new ArrayList<>(diaries.size());
        for (Diary diary : diaries) {
            DiaryVO diaryVO = DiaryVO.builder()
                    .moodEmoji(diary.getEmoji())
                    .moodLabel(diary.getEmotion())
                    .date(diary.getCreateTime())
                    .content(diary.getText())
                    .image(diary.getImage())
                    .build();
            diaryVO.setTags(JSON.parseArray(diary.getTags(), String.class));
            diaryVOs.add(diaryVO);
        }

        return diaryVOs;
    }


}
