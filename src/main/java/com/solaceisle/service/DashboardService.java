package com.solaceisle.service;

import com.solaceisle.pojo.po.MoodVO;
import com.solaceisle.pojo.po.TrackVO;

public interface DashboardService {

    MoodVO getMood();

    TrackVO getRecentTrack(int days);
}

