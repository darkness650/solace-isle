package com.solaceisle.service.impl;

import com.solaceisle.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.from}")
    private String fromAddress;
    public void sendTextEmail(String to, String subject, String text) {
        // Implementation for sending a text email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress); // 设置发件人
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
}
