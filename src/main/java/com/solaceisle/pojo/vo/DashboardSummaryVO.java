package com.solaceisle.pojo.vo;

import lombok.Data;

@Data
public class DashboardSummaryVO {
    private int achievementCount;        // 成就数量
    private int achievementProgressSum;  // 成就进度总和
    private int exerciseCount;           // 完成 CBT 练习数量
    private int likedCount;              // 点赞的安全空间数量
    private String currentMood;          // 当前心情（motto）
}

