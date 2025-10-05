package com.solaceisle.service;

import com.solaceisle.pojo.dto.CommentDTO;
import com.solaceisle.pojo.dto.PageQueryDTO;
import com.solaceisle.result.PageResult;

public interface SafeSpaceService {
    PageResult getSafeSpaces(PageQueryDTO pageQueryDTO);

    void likeOrUnlike(Integer id);

    void comment(CommentDTO commentDTO);
}
