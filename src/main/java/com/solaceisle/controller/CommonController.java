package com.solaceisle.controller;


import com.solaceisle.result.Result;
import com.solaceisle.util.AliOssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common/upload")
@Tag(name= "通用接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @Operation(description="文件上传")
    public Result<String> upload(MultipartFile file) throws IOException {
        log.info("文件上传{}",file.getOriginalFilename());
        String originalFilename = file.getOriginalFilename();
        String extension=originalFilename.substring(originalFilename.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString()+extension;
        String filePath=aliOssUtil.upload(file.getBytes(),uuid);
        return Result.success(filePath);
    }


}
