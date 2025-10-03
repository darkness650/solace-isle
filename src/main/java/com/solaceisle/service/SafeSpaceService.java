package com.solaceisle.service;

import com.solaceisle.pojo.dto.CommentDTO;
import com.solaceisle.pojo.dto.PageQuertDTO;
import com.solaceisle.pojo.po.SafeSpacePageVO;
import com.solaceisle.result.PageResult;

import java.util.List;

public interface SafeSpaceService {
    PageResult getSafeSpaces(PageQuertDTO pageQuertDTO);

    void likeOrUnlike(Integer id);

    void comment(CommentDTO commentDTO);
}
