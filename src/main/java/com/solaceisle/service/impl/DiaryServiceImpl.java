package com.solaceisle.service.impl;

import com.alibaba.fastjson2.JSON;
import com.solaceisle.constant.MessageConstant;
import com.solaceisle.context.BaseContext;
import com.solaceisle.exception.IllegalDateFormatException;
import com.solaceisle.mapper.DiaryMapper;
import com.solaceisle.pojo.dto.DiaryDTO;
import com.solaceisle.pojo.entity.Diary;
import com.solaceisle.pojo.vo.DiaryVO;
import com.solaceisle.service.DiaryService;
import io.github.imfangs.dify.client.DifyChatClient;
import io.github.imfangs.dify.client.enums.ResponseMode;
import io.github.imfangs.dify.client.exception.DifyApiException;
import io.github.imfangs.dify.client.model.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private static final String YEAR_MONTH_REGEX = "^\\d{4}-(0[1-9]|1[0-2])$";

    private final DiaryMapper diaryMapper;
    private final DifyChatClient tagGeneratorClient;

    @Override
    public List<DiaryVO> getMonthDiary(String yearMonth) {
        if (yearMonth == null || !yearMonth.matches(YEAR_MONTH_REGEX)) {
            throw new IllegalDateFormatException(MessageConstant.DATE_FORMAT_ERROR);
        }
        LocalDate start = LocalDate.parse(yearMonth + "-01");
        LocalDate end = start.plusMonths(1);
        String studentId = BaseContext.getCurrentId();

        List<Diary> diaries = diaryMapper.findByStudentIdAndYearMonth(studentId, start, end);

        List<DiaryVO> diaryVOs = new ArrayList<>(diaries.size());
        for (Diary diary : diaries) {
            DiaryVO diaryVO = new DiaryVO();
            BeanUtils.copyProperties(diary, diaryVO);
            diaryVOs.add(diaryVO);
        }

        return diaryVOs;
    }

    @Override
    public void addDiary(DiaryDTO diaryDTO) {
        Diary yesterdaysDiary = diaryMapper.findByStudentIdAndCreateTime(BaseContext.getCurrentId(), LocalDate.now().minusDays(1));
        Diary diary = new Diary();
        if(yesterdaysDiary==null)
        {
            diary.setConsecutivedays(1);
        }
        else
        {
            diary.setConsecutivedays(yesterdaysDiary.getConsecutivedays()+1);
        }
        BeanUtils.copyProperties(diaryDTO, diary);
        diary.setCreateTime(LocalDate.now());
        diary.setStudentId(BaseContext.getCurrentId());
        diary.setTags(diaryDTO.getTags().toString());
        diaryMapper.insert(diary);
    }

    @Override
    public List<String> getTags(String text) throws DifyApiException, IOException {
        ChatMessage message = ChatMessage.builder()
                .query(text)
                .responseMode(ResponseMode.BLOCKING)
                .user(BaseContext.getCurrentId())
                .build();
        String answer = tagGeneratorClient.sendChatMessage(message).getAnswer();
        return JSON.parseArray(answer, String.class);
    }
}

