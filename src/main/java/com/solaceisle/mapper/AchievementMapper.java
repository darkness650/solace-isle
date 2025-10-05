package com.solaceisle.mapper;

import com.solaceisle.pojo.entity.Achievement;
import com.solaceisle.pojo.entity.UserAchievement;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Mapper
public interface AchievementMapper {

    @Select("select * from achievement")
    List<Achievement> getAchievements();

    @MapKey("achievementId")
    @Select("select achievement_id, finish_time from user_achievement where student_id=#{studentId}")
    Map<String, UserAchievement> getAchievementsIds(String studentId);

    @Insert("insert into user_achievement(student_id, achievement_id, finish_time) values (#{studentId},#{achievementId},#{achieveTime})")
    void achieve(String studentId, String achievementId, LocalDateTime achieveTime);

    @Select("select max(progress) from user_achievement where achievement_id= 'STREAK_3'and student_id=#{studentId} ")
    Integer getMaxProcess(String studentId);

    @Insert("insert into user_achievement(student_id, achievement_id, progress, finish_time) values (#{studentId},'STREAK_3',#{process},null)")
    void recordConsecutiveDays(String studentId, int process);

    @Update("update user_achievement set progress = #{consecutiveDays} where achievement_id='STREAK_3' and student_id=#{studentId}")
    void updateConsecutiveDays(String studentId, int consecutiveDays);
}
