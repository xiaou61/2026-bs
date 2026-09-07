package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oa.entity.Document;
import com.oa.entity.User;
import com.oa.mapper.DocumentMapper;
import com.oa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentMapper documentMapper;
    private final UserMapper userMapper;

    @Value("${file.upload-path}")
    private String uploadPath;

    public Page<Document> getList(Integer pageNum, Integer pageSize, String keyword, String category) {
        Page<Document> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Document> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.like(Document::getName, keyword);
        }
        if (StringUtils.hasText(category)) {
            wrapper.eq(Document::getCategory, category);
        }
        wrapper.orderByDesc(Document::getCreateTime);
        Page<Document> result = documentMapper.selectPage(page, wrapper);
        result.getRecords().forEach(d -> {
            User user = userMapper.selectById(d.getUploaderId());
            if (user != null) d.setUploaderName(user.getRealName());
        });
        return result;
    }

    public Document upload(Long userId, MultipartFile file, String category) throws IOException {
        String originalName = file.getOriginalFilename();
        String ext = originalName.substring(originalName.lastIndexOf("."));
        String newName = UUID.randomUUID() + ext;
        File dir = new File(uploadPath);
        if (!dir.exists()) dir.mkdirs();
        File dest = new File(dir, newName);
        file.transferTo(dest);

        Document doc = new Document();
        doc.setName(originalName);
        doc.setPath(newName);
        doc.setSize(file.getSize());
        doc.setType(ext.substring(1));
        doc.setCategory(category != null ? category : "other");
        doc.setUploaderId(userId);
        doc.setIsShared(0);
        doc.setDownloadCount(0);
        documentMapper.insert(doc);
        return doc;
    }

    public Document getById(Long id) {
        return documentMapper.selectById(id);
    }

    public void update(Document document) {
        documentMapper.updateById(document);
    }

    public void delete(Long id) {
        Document doc = documentMapper.selectById(id);
        if (doc != null) {
            File file = new File(uploadPath, doc.getPath());
            if (file.exists()) file.delete();
            documentMapper.deleteById(id);
        }
    }

    public void incrementDownload(Long id) {
        Document doc = documentMapper.selectById(id);
        doc.setDownloadCount(doc.getDownloadCount() + 1);
        documentMapper.updateById(doc);
    }
}
