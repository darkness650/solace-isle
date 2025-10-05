package com.solaceisle.service.impl;

import com.alibaba.fastjson2.JSON;
import com.solaceisle.context.BaseContext;
import com.solaceisle.mapper.CBTMapper;
import com.solaceisle.pojo.dto.CBTDTO;
import com.solaceisle.pojo.entity.CbtExercise;
import com.solaceisle.pojo.entity.CbtExerciseDetail;
import com.solaceisle.pojo.vo.CBTDetailVO;
import com.solaceisle.pojo.vo.CBTVO;
import com.solaceisle.pojo.vo.chat.ErrorVO;
import com.solaceisle.pojo.vo.chat.MessageEndVO;
import com.solaceisle.pojo.vo.chat.MessageVO;
import com.solaceisle.pojo.vo.chat.PingVO;
import com.solaceisle.service.CBTService;
import io.github.imfangs.dify.client.DifyChatClient;
import io.github.imfangs.dify.client.callback.ChatStreamCallback;
import io.github.imfangs.dify.client.enums.ResponseMode;
import io.github.imfangs.dify.client.event.ErrorEvent;
import io.github.imfangs.dify.client.event.MessageEndEvent;
import io.github.imfangs.dify.client.event.MessageEvent;
import io.github.imfangs.dify.client.event.PingEvent;
import io.github.imfangs.dify.client.exception.DifyApiException;
import io.github.imfangs.dify.client.model.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class CBTServiceImpl implements CBTService {
    private final CBTMapper cbtMapper;
    private final DifyChatClient cbtAnalyzerClient;

    @Override
    @Transactional
    public List<CBTVO> getCBTs() {
        String studentId = BaseContext.getCurrentId();
        List<CbtExercise> cbts = cbtMapper.getCBT();
        Set<Long> doneIds = cbtMapper.getDoneCBTIds(studentId);
        List<CBTVO> cbtvos = new ArrayList<>();
        for (var cbt : cbts) {
            CBTVO cbtvo = new CBTVO();
            BeanUtils.copyProperties(cbt, cbtvo);
            cbtvo.setFinished(doneIds.contains(cbt.getId()));
            List<String> list = Arrays.stream(cbt.getTags().split(",")).toList();
            cbtvo.setTags(list);
            cbtvos.add(cbtvo);
        }
        return cbtvos;
    }

    @Override
    public List<CBTDetailVO> getCBTDetail(Integer id) {
        List<CbtExerciseDetail> details = cbtMapper.getCBTDetail(id);
        List<CBTDetailVO> cBTDetailVOs = new ArrayList<>();
        for (CbtExerciseDetail detail : details) {
            CBTDetailVO cbtDetailVO = new CBTDetailVO();
            BeanUtils.copyProperties(detail, cbtDetailVO);
            if ("evidence".equals(detail.getType())) {
                var placeholders = new CBTDetailVO.Placeholders();
                placeholders.setSupport(detail.getSupport());
                placeholders.setAgainst(detail.getAgainst());
                cbtDetailVO.setPlaceholders(placeholders);
            } else if ("single-select".equals(detail.getType())
                    || "multiple-select".equals(detail.getType())) {
                List<String> strings = JSON.parseArray(detail.getOptions(), String.class);
                cbtDetailVO.setOptions(strings);
            }
            cbtDetailVO.setId(detail.getId().toString());
            cBTDetailVOs.add(cbtDetailVO);
        }
        return cBTDetailVOs;
    }

    @Override
    public SseEmitter postCBT(List<CBTDTO> answer, Integer id) throws DifyApiException, IOException {
        List<CbtExerciseDetail> questions = cbtMapper.getCBTDetail(id);
        // 将问题和答案进行匹配
        List<Map.Entry<CbtExerciseDetail, CBTDTO>> qaPairs = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            qaPairs.add(Map.entry(questions.get(i), answer.get(i)));
        }

        // 调用AI进行分析
        SseEmitter emitter = new SseEmitter();

        var message = ChatMessage.builder()
                .user(BaseContext.getCurrentId())
                .query(JSON.toJSONString(qaPairs))
                .responseMode(ResponseMode.STREAMING)
                .inputs(Map.of())
                .build();

        cbtAnalyzerClient.sendChatMessageStream(message, new ChatStreamCallback() {
            @SneakyThrows
            @Override
            public void onMessage(MessageEvent event) {
                var messageVO = new MessageVO();
                BeanUtils.copyProperties(event, messageVO);
                emitter.send(messageVO);
            }

            @SneakyThrows
            @Override
            public void onMessageEnd(MessageEndEvent event) {
                var messageEndVO = new MessageEndVO();
                BeanUtils.copyProperties(event, messageEndVO);
                emitter.send(messageEndVO);
                emitter.complete();
            }

            @SneakyThrows
            @Override
            public void onError(ErrorEvent event) {
                var errorVO = new ErrorVO();
                BeanUtils.copyProperties(event, errorVO);
                emitter.send(errorVO);
                emitter.complete();
            }

            @SneakyThrows
            @Override
            public void onPing(PingEvent event) {
                var pingVO = new PingVO();
                BeanUtils.copyProperties(event, pingVO);
                emitter.send(pingVO);
            }
        });
        return emitter;
    }


}
