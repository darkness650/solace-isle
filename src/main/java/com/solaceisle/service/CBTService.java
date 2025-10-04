package com.solaceisle.service;

import com.solaceisle.pojo.dto.CBTDTO;
import com.solaceisle.pojo.po.CBTDeatilVO;
import com.solaceisle.pojo.po.CBTVO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface CBTService {
    List<CBTVO> getCBT();

    List<CBTDeatilVO> getCBTDetail(Integer id);


    SseEmitter postCBT(List<CBTDTO> answer, Integer id);
}
