package com.task_hou.controller;

import com.task_hou.common.CommonResult;
import com.task_hou.service.CaptchaService;
import com.task_hou.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;
    // 单文件上传
    @PostMapping("/upload/file")
    public CommonResult<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type) {
        return CommonResult.success(fileService.uploadFile(file, type));
    }

    // 多文件上传（图片）
    @PostMapping("/upload/files")
    public CommonResult<String[]> uploadFiles(@RequestParam("files[]") MultipartFile[] files) {
        return CommonResult.success(fileService.uploadFiles(files));
    }
}