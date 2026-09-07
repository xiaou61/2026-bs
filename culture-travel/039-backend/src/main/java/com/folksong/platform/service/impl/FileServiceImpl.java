package com.folksong.platform.service.impl;

import com.folksong.platform.exception.BusinessException;
import com.folksong.platform.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.upload-path}")
    private String uploadPath;

    @Override
    public String uploadFile(MultipartFile file, String type) {
        if (file.isEmpty()) {
            throw new BusinessException("上传文件不能为空");
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String dateFolder = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String relativePath = type + "/" + dateFolder;
        String fileName = UUID.randomUUID().toString().replace("-", "") + extension;
        Path dirPath = Paths.get(uploadPath, relativePath);
        try {
            Files.createDirectories(dirPath);
            Path filePath = dirPath.resolve(fileName);
            file.transferTo(filePath.toFile());
            return "/uploads/" + relativePath + "/" + fileName;
        } catch (IOException e) {
            throw new BusinessException("文件上传失败：" + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return;
        }
        String relativePath = filePath.replace("/uploads/", "");
        Path path = Paths.get(uploadPath, relativePath);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new BusinessException("文件删除失败：" + e.getMessage());
        }
    }
}
