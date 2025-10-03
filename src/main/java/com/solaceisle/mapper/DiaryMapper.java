package com.solaceisle.mapper;

import com.solaceisle.pojo.dto.DiaryDTO;
import com.solaceisle.pojo.entity.Diary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DiaryMapper {

    List<Diary> findByStudentIdAndYearMonth(String studentId, LocalDate start,LocalDate end);

    void insert(Diary diary);

    Diary findByStudentIdAndCreateTime(String currentId, LocalDate localDate);
}

