package com.pang.Controller;

import com.pang.Pojo.Result;
import com.pang.Utils.AliOSSUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequestMapping("/upload")
@RestController
public class UploadController {

    @Resource
    private AliOSSUtils aliOSSUtils;

    @PostMapping
    public Result upload(MultipartFile image) throws IOException {
        String url = aliOSSUtils.upload(image);
        //有几个小坑，文件名漏东西，传参传错，文件上传的容量需要考虑
        log.info("文件上传:{}" ,url);
        return Result.success(url);
    }
}
