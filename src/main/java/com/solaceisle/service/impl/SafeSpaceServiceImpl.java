package com.solaceisle.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.solaceisle.context.BaseContext;
import com.solaceisle.mapper.SafeSpaceMapper;
import com.solaceisle.pojo.dto.CommentDTO;
import com.solaceisle.pojo.dto.PageQueryDTO;
import com.solaceisle.pojo.entity.SafeSpace;
import com.solaceisle.pojo.entity.UserSafeSpaceLiked;
import com.solaceisle.pojo.vo.SafeSpacePageVO;
import com.solaceisle.result.PageResult;
import com.solaceisle.service.SafeSpaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class SafeSpaceServiceImpl implements SafeSpaceService {
    private final SafeSpaceMapper safeSpaceMapper;
    @Override
    public PageResult getSafeSpaces(PageQueryDTO pageQueryDTO) {
        String studentId= BaseContext.getCurrentId();
        pageQueryDTO.setStudentId(studentId);

        PageHelper.startPage(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        Page<SafeSpace> page=safeSpaceMapper.page(pageQueryDTO);
        List<SafeSpace> safeSpaces =page.getResult();

        List<SafeSpacePageVO> safeSpacePageVOS=new ArrayList<>();

        Set<Long> likedIds= safeSpaceMapper.getLikedIds(studentId);

        for(var safeSpace: safeSpaces){
            var safeSpacePageVO=new SafeSpacePageVO();
            BeanUtils.copyProperties(safeSpace,safeSpacePageVO);
            safeSpacePageVO.setLike(safeSpace.getLikeCount());
            safeSpacePageVO.setLiked(likedIds.contains(safeSpace.getId()));
            safeSpacePageVO.setDatetime(safeSpace.getCreateTime());
            safeSpacePageVOS.add(safeSpacePageVO);
        }
        return new PageResult(page.getTotal(), safeSpacePageVOS);
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
