package com.solaceisle.service;

public interface EmailService {

    void sendTextEmail(String to, String subject, String text);
}
