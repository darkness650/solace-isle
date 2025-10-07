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
import java.time.format.TextStyle;
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
        List<Diary> diaries = diaryMapper.getRecentTrack(currentUserIdOrThrow(), days);
        TrackVO trackVO = new TrackVO();

        List<Track> tracks = new ArrayList<>(days);
        LocalDate cursor = LocalDate.now();

        if (diaries == null || diaries.isEmpty()) {
            trackVO.setConsecutiveDays(0);
            // 无日记，直接从今天开始补满 days 天
            for (int i = 0; i < days; i++) {
                Track t = new Track();
                t.setDay(cursor.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA));
                tracks.add(t);
                cursor = cursor.minusDays(1);
            }
            trackVO.setMoodTrend(tracks.reversed());
            return trackVO;
        }

        // 连续天数取最新一条
        trackVO.setConsecutiveDays(diaries.get(0).getConsecutiveDays());

//        // 确保按时间从近到远
//        diaries.sort(Comparator.comparing(Diary::getCreateTime).reversed());

        for (Diary diary : diaries) {
            if (tracks.size() >= days) break;

            LocalDate d = diary.getCreateTime();
            int gap = (int) (cursor.toEpochDay() - d.toEpochDay()); // 需要补的空白天数（包含今天若缺）
            for (int i = 0; i < gap && tracks.size() < days; i++) {
                LocalDate missingDate = cursor.minusDays(i);
                Track t = new Track();
                t.setDay(missingDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA));
                tracks.add(t);
            }

            if (tracks.size() < days) {
                // 添加当前日记对应的轨迹
                Track t = new Track();
                t.setDay(d.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA));
                t.setLabel(diary.getEmoji());
                t.setScore(diary.getScore());
                tracks.add(t);
            }

            // 游标移到该日记的前一天，继续向过去补
            cursor = d.minusDays(1);
        }

        // 若仍不足 days，继续从游标向过去补空白
        while (tracks.size() < days) {
            Track t = new Track();
            t.setDay(cursor.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA));
            tracks.add(t);
            cursor = cursor.minusDays(1);
        }

        trackVO.setMoodTrend(tracks.reversed());
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

