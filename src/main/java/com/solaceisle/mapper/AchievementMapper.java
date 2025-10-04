package com.solaceisle.mapper;

import com.solaceisle.pojo.entity.Achievement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
@Mapper
public interface AchievementMapper {
    @Select("select * from achievement")
    List<Achievement> getAchievements();
    @MapKey("achievement_id")
    Map<Integer, LocalDateTime> getAchievementsIds(String studentId);
    @Insert("insert into user_achievement(student_id, achievement_id, finish_time) values (#{studentId},#{achievementId},#{achieveTime})")
    void achieve(String studentId, int i, LocalDateTime now);
    @Select("select max(process) from user_achievement where achievement_id=3 and student_id=#{studentId} ")
    int getMaxProcess(String studentId);
    @Insert("insert into user_achievement(student_id, achievement_id, process, finish_time) values (#{studentId},3,#{process},null)")
    void recordConsecutiveDays(String studentId, int process);
}
