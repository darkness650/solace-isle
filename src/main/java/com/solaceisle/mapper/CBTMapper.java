package com.solaceisle.mapper;

import com.solaceisle.annotation.AutoFill;
import com.solaceisle.pojo.entity.CbtExercise;
import com.solaceisle.pojo.entity.CbtExerciseDetail;
import com.solaceisle.pojo.enumeration.OperatorType;
import com.solaceisle.pojo.vo.CBTVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Mapper
public interface CBTMapper {

    @Select("select * from cbt_exercise")
    List<CbtExercise> getCBT();

    @Select("select cbt_exercise_id from user_cbt_exercise where student_id = #{studentId}")
    Set<Long> getDoneCBTIds(String studentId);

    @Select("select * from cbt_exercise_detail where cbt_exercise_id = #{exerciseId} order by id")
    List<CbtExerciseDetail> getCBTDetail(long exerciseId);

    @AutoFill(value = OperatorType.CBT)
    @Insert("insert into user_cbt_exercise(student_id, cbt_exercise_id,finish_time) values(#{studentId}, #{exerciseId}, #{finishTime})")
    void markDone(String studentId, Integer exerciseId, LocalDateTime finishTime);
}
