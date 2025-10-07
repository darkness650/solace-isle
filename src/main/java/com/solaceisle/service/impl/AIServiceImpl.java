package com.solaceisle.service.impl;

import com.alibaba.fastjson2.JSON;
import com.solaceisle.mapper.DiaryMapper;
import com.solaceisle.pojo.entity.Diary;
import com.solaceisle.pojo.vo.DiaryVO;
import com.solaceisle.service.AIService;
import com.solaceisle.util.EmailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    public List<DiaryVO> getWeekDiary(String id) {
        log.info("AI 调用获取本周日记接口，学号：{}", id);
        LocalDate end = LocalDate.now().plusDays(1);
        LocalDate start = end.minusDays(7);

        List<Diary> diaries = diaryMapper.findByStudentIdAndDateRange(id, start, end);

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
