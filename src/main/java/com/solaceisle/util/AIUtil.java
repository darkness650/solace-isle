package com.solaceisle.util;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AIUtil {
    @Operation(description = "传入一段文本，根据文本返回tag")
    //TODO 接入ai，现在先返回空列表
    public List<String> getTags(String text) {
        return List.of();
    }
}
