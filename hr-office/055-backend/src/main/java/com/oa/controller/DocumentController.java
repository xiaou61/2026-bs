package com.oa.controller;

import com.oa.common.Result;
import com.oa.entity.Document;
import com.oa.service.DocumentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService documentService;

    @Value("${file.upload-path}")
    private String uploadPath;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer pageNum,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String keyword, String category) {
        return Result.success(documentService.getList(pageNum, pageSize, keyword, category));
    }

    @PostMapping("/upload")
    public Result upload(HttpServletRequest request, @RequestParam("file") MultipartFile file, String category) throws IOException {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(documentService.upload(userId, file, category));
    }

    @PutMapping
    public Result update(@RequestBody Document document) {
        documentService.update(document);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        documentService.delete(id);
        return Result.success();
    }

    @GetMapping("/download/{id}")
    public void download(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Document doc = documentService.getById(id);
        if (doc == null) return;
        documentService.incrementDownload(id);
        File file = new File(uploadPath, doc.getPath());
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(doc.getName(), StandardCharsets.UTF_8));
        try (InputStream in = new FileInputStream(file); OutputStream out = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        }
    }
}
