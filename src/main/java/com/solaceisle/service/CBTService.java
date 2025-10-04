package com.solaceisle.service;

import com.solaceisle.pojo.dto.CBTDTO;
import com.solaceisle.pojo.vo.CBTDetailVO;
import com.solaceisle.pojo.vo.CBTVO;
import io.github.imfangs.dify.client.exception.DifyApiException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

public interface CBTService {

    List<CBTVO> getCBTs();

    List<CBTDetailVO> getCBTDetail(Integer id);

    SseEmitter postCBT(List<CBTDTO> answer, Integer id) throws DifyApiException, IOException;
}
