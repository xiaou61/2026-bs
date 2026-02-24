package com.autorepair.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.io.FileUtil;
import com.autorepair.common.BusinessException;
import com.autorepair.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    private static final long MAX_SIZE = 5 * 1024 * 1024;
    private static final Set<String> ALLOW_EXT = new HashSet<>(Arrays.asList("jpg", "jpeg", "png", "webp", "gif"));

    @Value("${file.upload-path:uploads/}")
    private String uploadPath;

    @PostMapping
    public Result<?> upload(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("请选择要上传的文件");
        }
        String ext = FileUtil.extName(file.getOriginalFilename());
        if (StrUtil.isBlank(ext) || !ALLOW_EXT.contains(ext.toLowerCase())) {
            throw new BusinessException("仅支持jpg/jpeg/png/webp/gif格式");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new BusinessException("文件大小不能超过5MB");
        }
        String fileName = IdUtil.simpleUUID();
        fileName = fileName + "." + ext.toLowerCase();
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File target = new File(dir, fileName);
        try {
            file.transferTo(target);
        } catch (Exception e) {
            throw new BusinessException("上传失败");
        }
        return Result.success("/uploads/" + fileName);
    }
}


