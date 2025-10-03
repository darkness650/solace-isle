package com.solaceisle.service.impl;

import com.solaceisle.constant.MessageConstant;
import com.solaceisle.context.BaseContext;
import com.solaceisle.exception.IllegalDateFormatException;
import com.solaceisle.mapper.DiaryMapper;
import com.solaceisle.pojo.entity.Diary;
import com.solaceisle.pojo.vo.DiaryVO;
import com.solaceisle.service.DiaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private static final String YEAR_MONTH_REGEX = "^\\d{4}-(0[1-9]|1[0-2])$";

    private final DiaryMapper diaryMapper;

    @Override
    public List<DiaryVO> getMonthDiary(String yearMonth) {
        if (yearMonth == null || !yearMonth.matches(YEAR_MONTH_REGEX)) {
            throw new IllegalDateFormatException(MessageConstant.DATE_FORMAT_ERROR);
        }

        String studentId = BaseContext.getCurrentId();
        List<Diary> diaries = diaryMapper.findByStudentIdAndYearMonth(studentId, yearMonth);

        List<DiaryVO> diaryVOs = new ArrayList<>(diaries.size());
        for (Diary diary : diaries) {
            DiaryVO diaryVO = new DiaryVO();
            BeanUtils.copyProperties(diary, diaryVO);
            diaryVOs.add(diaryVO);
        }

        return diaryVOs;
    }
}

