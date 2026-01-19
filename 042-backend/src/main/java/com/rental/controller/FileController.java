package com.rental.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.rental.common.BusinessException;
import com.rental.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传控制器
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Value("${file.upload-path:uploads}")
    private String uploadPath;

    @Value("${file.access-url:/uploads}")
    private String accessUrl;

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public Result<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("请选择文件");
        }

        // 验证文件类型
        String originalFilename = file.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        if (!isAllowedType(suffix)) {
            throw new BusinessException("不支持的文件类型");
        }

        // 验证文件大小 (最大5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new BusinessException("文件大小不能超过5MB");
        }

        // 生成文件名
        String newFilename = IdUtil.simpleUUID() + "." + suffix;

        // 创建目录
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 保存文件
        File dest = new File(uploadDir, newFilename);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new BusinessException("文件上传失败");
        }

        Map<String, String> result = new HashMap<>();
        result.put("url", accessUrl + "/" + newFilename);
        result.put("filename", newFilename);

        return Result.success(result);
    }

    private boolean isAllowedType(String suffix) {
        if (suffix == null) return false;
        suffix = suffix.toLowerCase();
        return suffix.equals("jpg") || suffix.equals("jpeg") ||
                suffix.equals("png") || suffix.equals("gif") ||
                suffix.equals("webp");
    }
}
