package com.solaceisle.mapper;

import com.github.pagehelper.Page;
import com.solaceisle.pojo.dto.CommentDTO;
import com.solaceisle.pojo.dto.PageQuertDTO;
import com.solaceisle.pojo.entity.UserSafeSpaceLiked;
import com.solaceisle.pojo.vo.SafeSpacePageVO;
import org.apache.ibatis.annotations.*;

import java.util.Set;

@Mapper
public interface SafeSpaceMapper {

    Page<SafeSpacePageVO> page(PageQuertDTO pageQuertDTO);
    @Select("select safe_space_id from user_safe_space_liked where student_id=#{studentId}")
    Set<Integer> getLikedIds(String studentId);

    @Select("select * from user_safe_space_liked where student_id=#{studentId} and safe_space_id=#{id}")
    UserSafeSpaceLiked getSafeSpaceLiked(String studentId, Integer id);
    @Insert("insert into user_safe_space_liked(student_id, safe_space_id) VALUES (#{studentId}, #{id})")
    void like(String studentId, Integer id);
    @Delete("delete from user_safe_space_liked where student_id=#{studentId} and safe_space_id=#{id}")
    void unlike(String studentId, Integer id);
    @Update("update safe_space set like_count=like_count+#{i} where id=#{id}")
    void setLikes(Integer id, int i);
    @Insert("insert into safe_space(student_id, emoji, text) VALUES (#{studentId}, #{emoji}, #{text})")
    void insert(CommentDTO commentDTO);
}
