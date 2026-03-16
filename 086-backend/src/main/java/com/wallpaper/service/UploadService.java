package com.wallpaper.service;

import com.wallpaper.common.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Map<String, Object> uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("请选择图片文件");
        }
        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(originalFilename);
        List<String> allowExt = Arrays.asList("jpg", "jpeg", "png", "webp");
        if (!allowExt.contains(extension)) {
            throw new BusinessException("仅支持 jpg、jpeg、png、webp 图片");
        }
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new BusinessException("图片大小不能超过10MB");
        }
        String day = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        File dir = new File(uploadDir + File.separator + "image" + File.separator + day);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new BusinessException("创建上传目录失败");
        }
        String fileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        File target = new File(dir, fileName);
        try {
            file.transferTo(target);
        } catch (IOException e) {
            throw new BusinessException("图片上传失败");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("url", "/files/image/" + day + "/" + fileName);
        result.put("fileName", fileName);
        result.put("size", file.getSize());
        return result;
    }

    private String getExtension(String originalFilename) {
        if (originalFilename == null || !originalFilename.contains(".")) {
            return "";
        }
        return originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
    }
}
