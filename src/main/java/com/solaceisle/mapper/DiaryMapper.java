package com.solaceisle.mapper;

import com.solaceisle.pojo.entity.Diary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DiaryMapper {

    List<Diary> findByStudentIdAndYearMonth(String studentId, String yearMonth);
}

