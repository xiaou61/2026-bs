package com.xiaou.campusvideo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {
    
    @Value("${file.upload.video-path}")
    private String videoPath;
    
    @Value("${file.upload.cover-path}")
    private String coverPath;
    
    @Value("${file.upload.avatar-path}")
    private String avatarPath;
    
    public String uploadVideo(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }
        
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        
        File dir = new File(videoPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        File destFile = new File(videoPath + fileName);
        file.transferTo(destFile);
        
        return "/videos/" + fileName;
    }
    
    public String uploadCover(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }
        
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        
        File dir = new File(coverPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        File destFile = new File(coverPath + fileName);
        file.transferTo(destFile);
        
        return "/covers/" + fileName;
    }
    
    public String uploadAvatar(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }
        
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        
        File dir = new File(avatarPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        File destFile = new File(avatarPath + fileName);
        file.transferTo(destFile);
        
        return "/avatars/" + fileName;
    }
}

