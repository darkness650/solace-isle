package com.solaceisle.service;

import com.solaceisle.pojo.vo.DiaryVO;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.List;

public interface AIService {

    @Tool(description = "获取心理健康中心热线电话")
    String getHotline();

    @Tool(description = "发送文本邮件")
    void sendTextEmail(
            @ToolParam(description = "接收方邮箱地址") String to,
            @ToolParam(description = "主题") String subject,
            @ToolParam(description = "文本内容") String text);

    @Tool(description = "获取某月的日记列表")
    List<DiaryVO> getMonthDiary(
            @ToolParam(description = "某年某月，格式为yyyy-MM") String yearMonth);
}
