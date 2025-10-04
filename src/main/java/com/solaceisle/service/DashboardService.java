package com.solaceisle.service;

import com.solaceisle.pojo.vo.MoodVO;
import com.solaceisle.pojo.vo.TrackVO;

public interface DashboardService {

    MoodVO getMood();

    TrackVO getRecentTrack(int days);
}

