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
import io.github.imfangs.dify.client.DifyCompletionClient;
import io.github.imfangs.dify.client.enums.FileTransferMethod;
import io.github.imfangs.dify.client.enums.FileType;
import io.github.imfangs.dify.client.enums.ResponseMode;
import io.github.imfangs.dify.client.exception.DifyApiException;
import io.github.imfangs.dify.client.model.chat.ChatMessage;
import io.github.imfangs.dify.client.model.completion.CompletionRequest;
import io.github.imfangs.dify.client.model.file.FileInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private static final String YEAR_MONTH_REGEX = "^\\d{4}-(0[1-9]|1[0-2])$";

    private final DiaryMapper diaryMapper;
    private final DifyChatClient tagGeneratorClient;
    private final DifyCompletionClient diaryEmotionRankClient;

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
//    @Transactional
    public void addDiary(DiaryDTO diaryDTO) throws DifyApiException, IOException {
        Diary yesterdaysDiary = diaryMapper.findByStudentIdAndCreateTime(BaseContext.getCurrentId(), LocalDate.now().minusDays(1));
        Diary diary = new Diary();
        if (yesterdaysDiary == null) {
            diary.setConsecutiveDays(1);
        } else {
            diary.setConsecutiveDays(yesterdaysDiary.getConsecutiveDays() + 1);
        }
        diary.setEmoji(diaryDTO.getMoodEmoji());
        diary.setEmotion(diaryDTO.getMoodLabel());
        diary.setText(diaryDTO.getContent());
        diary.setImage(diaryDTO.getImage());
        diary.setCreateTime(LocalDate.now());
        diary.setStudentId(BaseContext.getCurrentId());
        diary.setTags(diaryDTO.getTags().toString());

        // 调用AI进行打分
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("emoji", diary.getEmoji());
        inputs.put("emotion", diary.getEmotion());
        inputs.put("text", diary.getText());
        inputs.put("tags", diary.getTags());

        List<FileInfo> fileInfos=null;
        String imageUrl=diary.getImage();
        if(imageUrl!=null){
            var fileInfo = FileInfo.builder()
                    .type(FileType.IMAGE)
                    .transferMethod(FileTransferMethod.REMOTE_URL)
                    .url(imageUrl)
                    .build();
            fileInfos=new ArrayList<>();
            fileInfos.add(fileInfo);
        }

        var request = CompletionRequest.builder()
                .user(BaseContext.getCurrentId())
                .inputs(inputs)
                .responseMode(ResponseMode.BLOCKING)
                .files(fileInfos)
                .build();

        var response = diaryEmotionRankClient.sendCompletionMessage(request);
        String answer = response.getAnswer();
        diary.setScore(Integer.parseInt(answer));


        diaryMapper.insert(diary);
    }

    @Override
    public List<String> getTags(String text) throws DifyApiException, IOException {
        ChatMessage message = ChatMessage.builder()
                .query(text)
                .responseMode(ResponseMode.BLOCKING)
                .user(BaseContext.getCurrentId())
                .inputs(Map.of())
                .build();
        String answer = tagGeneratorClient.sendChatMessage(message).getAnswer();
        return JSON.parseArray(answer, String.class);
    }
}

