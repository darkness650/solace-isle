package com.solaceisle.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.solaceisle.context.BaseContext;
import com.solaceisle.mapper.SafeSpaceMapper;
import com.solaceisle.pojo.dto.CommentDTO;
import com.solaceisle.pojo.dto.PageQuertDTO;
import com.solaceisle.pojo.entity.UserSafeSpaceLiked;
import com.solaceisle.pojo.po.SafeSpacePageVO;
import com.solaceisle.result.PageResult;
import com.solaceisle.service.SafeSpaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SafeSpaceServiceImpl implements SafeSpaceService {
    private final SafeSpaceMapper safeSpaceMapper;
    @Override
    public PageResult getSafeSpaces(PageQuertDTO pageQuertDTO) {
        String StudentId= BaseContext.getCurrentId();
        pageQuertDTO.setStudentId(StudentId);
        PageHelper.startPage(pageQuertDTO.getPage(), pageQuertDTO.getPageSize());
        Page<SafeSpacePageVO> page=safeSpaceMapper.page(pageQuertDTO);
        List<SafeSpacePageVO> safeSpacePageVOList=page.getResult();
        Set<Integer> likedIds= safeSpaceMapper.getLikedIds(StudentId);
        for(SafeSpacePageVO safeSpacePageVO:safeSpacePageVOList){
            if(likedIds.contains(safeSpacePageVO.getId())){
                safeSpacePageVO.setIsliked(true);
            }else{
                safeSpacePageVO.setIsliked(false);
            }
        }
        return new PageResult(page.getTotal(), safeSpacePageVOList);
    }
    @Transactional
    @Override
    public void likeOrUnlike(Integer id) {
        String StudentId=BaseContext.getCurrentId();
        UserSafeSpaceLiked userSafeSpaceLiked=safeSpaceMapper.getSafeSpaceLiked(StudentId,id);
        if(userSafeSpaceLiked==null){
            safeSpaceMapper.like(StudentId,id);
            safeSpaceMapper.setLikes(id,1);
        }
        else
        {
            safeSpaceMapper.unlike(StudentId,id);
            safeSpaceMapper.setLikes(id,-1);
        }
    }

    @Override
    public void comment(CommentDTO commentDTO) {
        String StudentId=BaseContext.getCurrentId();
        commentDTO.setStudentId(StudentId);
        commentDTO.setDate(LocalDateTime.now());
        safeSpaceMapper.insert(commentDTO);
    }
}
