package com.solaceisle.controller;

import com.solaceisle.pojo.po.MoodVO;
import com.solaceisle.pojo.po.TrackVO;
import com.solaceisle.result.Result;
import com.solaceisle.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

