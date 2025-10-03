package com.solaceisle.service.impl;

import com.solaceisle.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    @Value("${solace.hotline}")
    private String hotline;

    @Override
    public String getHotline() {
        return hotline;
    }
}
