package com.solaceisle.controller;

import com.solaceisle.pojo.vo.AchievementsVO;
import com.solaceisle.pojo.vo.MoodVO;
import com.solaceisle.pojo.vo.TrackVO;
import com.solaceisle.result.Result;
import com.solaceisle.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/remind")
    public Result<List<String>> getRemind() {
        return Result.success(dashboardService.getRemind());
    }
    @GetMapping("/achievements")
    public Result<List<AchievementsVO>> getAchievements() {
        return Result.success(dashboardService.getAchievements());
    }
}

