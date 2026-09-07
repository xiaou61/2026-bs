package com.xiaou.collabboard.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {

    @Value("${file.upload.path}")
    private String uploadPath;

    @Value("${file.upload.attachment-path}")
    private String attachmentPath;

    @Value("${file.upload.avatar-path}")
    private String avatarPath;

    @Value("${file.upload.cover-path}")
    private String coverPath;

    public String saveAvatar(MultipartFile file) throws IOException {
        return saveFile(file, avatarPath);
    }

    public String saveCover(MultipartFile file) throws IOException {
        return saveFile(file, coverPath);
    }

    public String saveAttachment(MultipartFile file) throws IOException {
        return saveFile(file, attachmentPath);
    }

    private String saveFile(MultipartFile file, String path) throws IOException {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : "";
        String filename = UUID.randomUUID().toString() + suffix;
        
        File destFile = new File(dir, filename);
        file.transferTo(destFile);
        
        return filename;
    }

    public boolean deleteFile(String filename, String type) {
        String path = switch (type) {
            case "avatar" -> avatarPath;
            case "cover" -> coverPath;
            case "attachment" -> attachmentPath;
            default -> uploadPath;
        };
        
        File file = new File(path, filename);
        return file.exists() && file.delete();
    }
}

