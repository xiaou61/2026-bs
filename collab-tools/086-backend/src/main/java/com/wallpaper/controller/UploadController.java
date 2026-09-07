package com.wallpaper.controller;

import com.wallpaper.common.Result;
import com.wallpaper.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/image")
    public Result<Map<String, Object>> image(@RequestPart("file") MultipartFile file) {
        return Result.success(uploadService.uploadImage(file));
    }
}
