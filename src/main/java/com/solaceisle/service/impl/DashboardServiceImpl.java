package com.solaceisle.service.impl;

import com.solaceisle.context.BaseContext;
import com.solaceisle.exception.BaseException;
import com.solaceisle.mapper.DashboardMapper;
import com.solaceisle.pojo.entity.Diary;
import com.solaceisle.pojo.entity.Track;
import com.solaceisle.pojo.po.MoodVO;
import com.solaceisle.pojo.po.TrackVO;
import com.solaceisle.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardMapper dashboardMapper;

    private String currentUserIdOrThrow() {
        String id = BaseContext.getCurrentId();
        if (id == null || id.isBlank()) {
            throw new BaseException("未登录，无法获取仪表盘数据");
        }
        return id;
    }


    @Override
    public MoodVO getMood() {
        String studentId = currentUserIdOrThrow();
        Diary diary = dashboardMapper.getCurrentMood(studentId);
        MoodVO moodVO = new MoodVO();
        moodVO.setEmoji(diary.getEmoji());
        moodVO.setDescription(diary.getText());
        moodVO.setLabel(diary.getEmotion());
        return moodVO;
    }

    @Override
    public TrackVO getRecentTrack(int days) {

        List<Diary> diaries = dashboardMapper.getRecentTrack(currentUserIdOrThrow(), days);
        TrackVO trackVO = new TrackVO();
        trackVO.setConsecutiveDays(diaries.get(0).getConsecutiveDays());
        List<Track> tracks = new ArrayList<>();
        LocalDate lastDiary = LocalDate.now();
        for (Diary diary : diaries) {
            if (tracks.size() >= days) break;
            Track track = new Track();
            int daysBetween = (int) (lastDiary.toEpochDay() - diary.getCreateTime().toEpochDay());
            for (int i = 0; i < daysBetween - 1; i++) {
                tracks.add(new Track());
            }
            track.setDay(String.valueOf(diary.getCreateTime().getDayOfWeek()));
            track.setLabel(diary.getEmoji());
            track.setScore(diary.getScore());
            tracks.add(track);
        }
        if (tracks.size() < days) {
            int remainingDays = days - tracks.size();
            for (int i = 0; i < remainingDays; i++) {
                tracks.add(new Track());
            }
        }
        trackVO.setTracks(tracks);
        return trackVO;
    }

}

