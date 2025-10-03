package com.solaceisle.service;

import com.solaceisle.pojo.dto.DiaryDTO;
import com.solaceisle.pojo.vo.DiaryVO;
import io.github.imfangs.dify.client.exception.DifyApiException;

import java.io.IOException;
import java.util.List;

public interface DiaryService {

    List<DiaryVO> getMonthDiary(String yearMonth);

    void addDiary(DiaryDTO diaryDTO);

    List<String> getTags(String text) throws DifyApiException, IOException;
}

