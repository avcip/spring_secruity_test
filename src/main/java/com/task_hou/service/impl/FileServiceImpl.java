package com.task_hou.service.impl;

import com.task_hou.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadFile(MultipartFile file, String type) {
        // 校验文件类型（示例简化，实际需检查MIME类型）
        if (!"image".equals(type) && !"document".equals(type)) {
            throw new IllegalArgumentException("文件类型仅支持image或document");
        }

        // 模拟OSS上传：生成随机文件名 + 固定OSS路径
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        return "https://oss.example.com/" + type + "/" + fileName;
    }

    @Override
    public String[] uploadFiles(MultipartFile[] files) {
        String[] urls = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            urls[i] = uploadFile(files[i], "image"); // 多文件上传固定为图片类型
        }
        return urls;
    }
}