package com.solaceisle.service;

import com.solaceisle.pojo.dto.CommentDTO;
import com.solaceisle.pojo.dto.PageQuertDTO;
import com.solaceisle.result.PageResult;

public interface SafeSpaceService {
    PageResult getSafeSpaces(PageQuertDTO pageQuertDTO);

    void likeOrUnlike(Integer id);

    void comment(CommentDTO commentDTO);
}
