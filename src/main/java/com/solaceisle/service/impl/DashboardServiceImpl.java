package com.solaceisle.service.impl;

import com.solaceisle.context.BaseContext;
import com.solaceisle.exception.BaseException;
import com.solaceisle.mapper.AchievementMapper;
import com.solaceisle.mapper.DiaryMapper;
import com.solaceisle.pojo.entity.Achievement;
import com.solaceisle.pojo.entity.Diary;
import com.solaceisle.pojo.entity.Track;
import com.solaceisle.pojo.entity.UserAchievement;
import com.solaceisle.pojo.vo.AchievementVO;
import com.solaceisle.pojo.vo.MoodVO;
import com.solaceisle.pojo.vo.TrackVO;
import com.solaceisle.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final DiaryMapper diaryMapper;
    private final AchievementMapper achievementMapper;
    private final RedisTemplate redisTemplate;
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
        Diary diary = diaryMapper.getCurrentMood(studentId);
        if(diary==null){
            return new MoodVO("😶","无记录","今天还没有写日记哦~");
        }
        MoodVO moodVO = new MoodVO();
        moodVO.setEmoji(diary.getEmoji());
        moodVO.setDescription(diary.getText());
        moodVO.setLabel(diary.getEmotion());
        return moodVO;
    }

    @Override
    public TrackVO getRecentTrack(int days) {
        // Mapper已按日期升序排序
        List<Diary> diaries = diaryMapper.getRecentTrack(currentUserIdOrThrow(), days);
        TrackVO trackVO = new TrackVO();
        if(diaries==null||diaries.isEmpty()){
            trackVO.setConsecutiveDays(0);
        }
        else
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
            // TODO SUNDAY MONDAY ，改为中文 周日 周一 ...
            track.setDay(String.valueOf(diary.getCreateTime().getDayOfWeek()));
            track.setLabel(diary.getEmoji());
            track.setScore(diary.getScore());
            tracks.add(track);
        }
        // TODO 补齐前面的空白天数，而不是后面的
        if (tracks.size() < days) {
            int remainingDays = days - tracks.size();
            for (int i = 0; i < remainingDays; i++) {
                tracks.add(new Track());
            }
        }
        trackVO.setMoodTrend(tracks);
        return trackVO;
    }

    @Override
    public List<String> getRemind() {
        String studentId = currentUserIdOrThrow();
        HashMap<String, String> map = (HashMap<String, String>) redisTemplate.opsForHash().entries(studentId);
        List<String> reminds = new ArrayList<>();
        List<String> deleteKeys = new ArrayList<>();
        for (String key : map.keySet()) {
            if(key.equals("sessionId")){
                continue;
            }
            reminds.add(map.get(key));
            deleteKeys.add(key);
        }
        if(!deleteKeys.isEmpty()){
            redisTemplate.opsForHash().delete(studentId,deleteKeys.toArray());
        }
        return reminds;
    }

    @Override
    public List<AchievementVO> getAchievements() {
        String studentId = currentUserIdOrThrow();
        List<Achievement> achievements= achievementMapper.getAchievements();
        List<AchievementVO> achievementVOS = new ArrayList<>();
        Map<String, UserAchievement> achieve_Achievements = achievementMapper.getAchievementsIds(studentId);
        for(Achievement achievement:achievements){
            AchievementVO achievementVO = new AchievementVO();
            BeanUtils.copyProperties(achievement, achievementVO);
            achievementVO.setName(achievement.getTitle());
            if(achieve_Achievements.containsKey(achievement.getId())){
                var finishTime = achieve_Achievements.get(achievement.getId()).getFinishTime();
                achievementVO.setAchievedAt(finishTime);
            }
            else achievementVO.setAchievedAt(null);
            achievementVOS.add(achievementVO);
        }
        return achievementVOS;
    }

}

