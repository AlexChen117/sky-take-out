package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Alex
 * @version 1.0
 * @project sky-take-out
 * @date 2023/12/5 11:42:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {
    private final AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        try {
            String oldFileName = file.getOriginalFilename();
            if (Objects.nonNull(oldFileName)) {
                String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));
                String url = aliOssUtil.upload(file.getBytes(), newFileName);
                return Result.success(url);
            }
        } catch (IOException e) {
            log.info("-----文件上传失败-----", e);
        }
        return Result.error("文件上传失败");
    }
}
