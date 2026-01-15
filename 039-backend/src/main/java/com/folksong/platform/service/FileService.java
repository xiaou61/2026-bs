package com.folksong.platform.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file, String type);
    void deleteFile(String filePath);
}
