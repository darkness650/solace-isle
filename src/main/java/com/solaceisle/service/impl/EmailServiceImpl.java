package com.solaceisle.service.impl;

import com.solaceisle.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.from}")
    private String fromAddress;

    @Override
    public void sendTextEmail(String to, String subject, String text) {
        // 基本参数校验，避免空值引发的无意义连接尝试
        if (!StringUtils.hasText(to)) {
            log.warn("Skip sending email: 'to' is blank");
            return;
        }
        if (!StringUtils.hasText(subject)) {
            subject = ""; // 避免 NPE
        }
        if (text == null) {
            text = "";
        }
        if (!StringUtils.hasText(fromAddress)) {
            log.error("spring.mail.from 未配置或为空，取消发送");
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress.trim());
        message.setTo(to.trim());
        message.setSubject(subject);
        message.setText(text);
        try {
            long start = System.currentTimeMillis();
            mailSender.send(message);
            log.info("Email sent to {} in {} ms. subject='{}'", to, System.currentTimeMillis() - start, subject);
        } catch (MailException ex) {
            // 记录关键信息帮助定位 (端口/主机/网络)
            log.error("Failed to send email to {}. subject='{}', cause: {}", to, subject, ex.getMessage(), ex);
            // 可以视业务抛出自定义异常，这里先不再向外抛，避免影响主流程（如验证码接口返回）
        }
    }
}
