package com.solaceisle.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solaceisle.context.BaseContext;
import com.solaceisle.mapper.CBTMapper;
import com.solaceisle.pojo.dto.CBTDTO;
import com.solaceisle.pojo.entity.CbtExerciseDetail;
import com.solaceisle.pojo.po.CBTDeatilVO;
import com.solaceisle.pojo.po.CBTVO;
import com.solaceisle.service.CBTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CBTServiceImpl implements CBTService {
    private final CBTMapper cbtMapper;
    @Override
    public List<CBTVO> getCBT() {
        String studentId = BaseContext.getCurrentId();
        List<CBTVO> cbt = cbtMapper.getCBT();
        Set<Long> doneIds = cbtMapper.getDoneCBTIds(studentId);
        for (CBTVO cbtVO : cbt) {
            cbtVO.setFinished(doneIds.contains(cbtVO.getId()));
        }
        return cbt;
    }

    @Override
    public List<CBTDeatilVO> getCBTDetail(Integer id) {
        List<CbtExerciseDetail> details = cbtMapper.getCBTDetail(id);
        List<CBTDeatilVO> cBTDeatilVOs = new ArrayList<>();
        for(CbtExerciseDetail detail:details){
            CBTDeatilVO cbtDeatilVO = new CBTDeatilVO();
            BeanUtils.copyProperties(detail, cbtDeatilVO);
            if("multiple_choice".equals(detail.getType()) || "single_choice".equals(detail.getType()));
            {
                ObjectMapper mapper = new ObjectMapper();
                List<String> list = null;
                try {
                    list = mapper.readValue(detail.getOptions(), new TypeReference<List<String>>() {});
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                cbtDeatilVO.setOptions(list);
            }
            if("evidence".equals(detail.getType()))
            {
                cbtDeatilVO.setPlaceholders(Map.of("support",detail.getSupport(),"against",detail.getAgainst()));
            }
            cBTDeatilVOs.add(cbtDeatilVO);
        }
        return cBTDeatilVOs;
    }

    @Override
    public SseEmitter postCBT(List<CBTDTO> answer, Integer id) {
        List<CBTVO> questions = cbtMapper.getCBT();
        //TODO 用ai判题，question是题目，answer是答案
        return null;
    }


}
