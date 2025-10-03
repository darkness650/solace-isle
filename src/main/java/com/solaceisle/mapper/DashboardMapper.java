package com.solaceisle.mapper;

import com.solaceisle.pojo.entity.Diary;
import com.solaceisle.pojo.entity.UserAchievement;
import com.solaceisle.pojo.entity.UserCbtExercise;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DashboardMapper {

    Diary getCurrentMood(String studentId); // motto 字段


    List<Diary> getRecentTrack(String studentId, int days);
}
