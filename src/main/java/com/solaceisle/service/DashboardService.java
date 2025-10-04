package com.solaceisle.service;

import com.solaceisle.pojo.po.MoodVO;
import com.solaceisle.pojo.po.TrackVO;

import java.util.List;

public interface DashboardService {

    MoodVO getMood();

    TrackVO getRecentTrack(int days);

    List<String> getRemind();
}

