package com.solaceisle.mapper;

import com.solaceisle.pojo.entity.CbtExerciseDetail;
import com.solaceisle.pojo.vo.CBTVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface CBTMapper {

    @Select("select * from cbt_exercise")
    List<CBTVO> getCBT();

    @Select("select cbt_exercise_id from user_cbt_exercise where student_id = #{studentId}")
    Set<Long> getDoneCBTIds(String studentId);

    @Select("select * from cbt_exercise_detail where cbt_exercise_id = #{exerciseId} order by id")
    List<CbtExerciseDetail> getCBTDetail(long exerciseId);
}
