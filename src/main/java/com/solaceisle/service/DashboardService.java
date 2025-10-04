package com.solaceisle.service;

import com.solaceisle.pojo.vo.MoodVO;
import com.solaceisle.pojo.vo.TrackVO;

import java.util.List;

public interface DashboardService {

    MoodVO getMood();

    TrackVO getRecentTrack(int days);

    List<String> getRemind();
}

