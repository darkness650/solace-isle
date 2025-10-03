package com.solaceisle.controller;

import com.solaceisle.pojo.dto.UpdateMoodDTO;
import com.solaceisle.pojo.entity.UserAchievement;
import com.solaceisle.pojo.entity.UserCbtExercise;
import com.solaceisle.pojo.po.DashboardSummaryVO;
import com.solaceisle.pojo.po.MoodVO;
import com.solaceisle.pojo.po.TrackVO;
import com.solaceisle.result.Result;
import com.solaceisle.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    // 当前心情
    @GetMapping("/currentMood")
    public Result<MoodVO> getMood() {
        return Result.success(dashboardService.getMood());
    }

    @GetMapping("/recentTrack")
    public Result<TrackVO> getRecentTrack(@RequestParam int days) {
        return Result.success(dashboardService.getRecentTrack(days));
    }
}

