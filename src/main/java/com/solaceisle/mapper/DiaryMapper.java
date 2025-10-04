package com.solaceisle.mapper;

import com.solaceisle.annotation.AutoFill;
import com.solaceisle.pojo.dto.DiaryDTO;
import com.solaceisle.pojo.entity.Diary;
import com.solaceisle.pojo.enumeration.OperatorType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DiaryMapper {

    List<Diary> findByStudentIdAndYearMonth(String studentId, LocalDate start,LocalDate end);
    @AutoFill(value = OperatorType.DIARY)
    void insert(Diary diary);

    Diary findByStudentIdAndCreateTime(String currentId, LocalDate localDate);

    Integer getSumDiary(String studentId);

    Diary getCurrentMood(String studentId); // motto 字段


    List<Diary> getRecentTrack(String studentId, int days);

}

