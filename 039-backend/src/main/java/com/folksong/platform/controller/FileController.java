package com.folksong.platform.controller;

import com.folksong.platform.common.Result;
import com.folksong.platform.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件管理")
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "上传图片")
    @PostMapping("/upload/image")
    public Result<String> uploadImage(@RequestParam("file") MultipartFile file) {
        String url = fileService.uploadFile(file, "images");
        return Result.success(url);
    }

    @Operation(summary = "上传音频")
    @PostMapping("/upload/audio")
    public Result<String> uploadAudio(@RequestParam("file") MultipartFile file) {
        String url = fileService.uploadFile(file, "audios");
        return Result.success(url);
    }

    @Operation(summary = "上传视频")
    @PostMapping("/upload/video")
    public Result<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        String url = fileService.uploadFile(file, "videos");
        return Result.success(url);
    }
}
