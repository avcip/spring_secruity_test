package com.task_hou.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 单文件上传（图片/文档）
     * @param file 上传的文件
     * @param type 文件类型（image/document）
     * @return 文件存储的OSS完整URL
     */
    String uploadFile(MultipartFile file, String type);

    /**
     * 多文件上传（批量图片）
     * @param files 多个图片文件数组
     * @return 文件存储的OSS完整URL数组
     */
    String[] uploadFiles(MultipartFile[] files);
}