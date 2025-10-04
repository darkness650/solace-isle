package com.solaceisle.controller;

import com.solaceisle.pojo.dto.CommentDTO;
import com.solaceisle.pojo.dto.PageQuertDTO;
import com.solaceisle.result.PageResult;
import com.solaceisle.result.Result;
import com.solaceisle.service.SafeSpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/safeSpace")
public class SafeSpaceController {

    private final SafeSpaceService safeSpaceService;
    @GetMapping
    public Result<PageResult> getSafeSpaces(@RequestParam int order, @RequestParam int page,@RequestParam int pageSize) {
        PageResult pageVOList= safeSpaceService.getSafeSpaces(new PageQuertDTO(order,page,pageSize));
        return Result.success(pageVOList);
    }
    @PutMapping("like")
    public Result likeOrUnlike(@RequestParam Integer id) {
        safeSpaceService.likeOrUnlike(id);
        return Result.success();
    }
    @PostMapping
    public Result addSafeSpace(@RequestBody CommentDTO commentDTO) {
        safeSpaceService.comment(commentDTO);
        return Result.success();
    }
}
