package com.solaceisle.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {
    String emoji;
    String text;
    String studentId;
    LocalDateTime date;
}
