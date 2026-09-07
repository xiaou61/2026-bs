package com.xiaou.collabboard.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.collabboard.entity.Attachment;
import com.xiaou.collabboard.mapper.AttachmentMapper;
import com.xiaou.collabboard.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttachmentService extends ServiceImpl<AttachmentMapper, Attachment> {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FileUtil fileUtil;

    public List<Attachment> listAttachments(Long documentId, Long userId) {
        documentService.getReadableDocument(documentId, userId);
        return this.lambdaQuery()
                .eq(Attachment::getDocumentId, documentId)
                .orderByDesc(Attachment::getCreateTime)
                .list();
    }

    public Attachment uploadAttachment(Long documentId, Long userId, MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("请先选择要上传的附件");
        }

        documentService.getEditableDocument(documentId, userId);
        String storedFilename = fileUtil.saveAttachment(file);

        Attachment attachment = new Attachment();
        attachment.setDocumentId(documentId);
        attachment.setUserId(userId);
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileSize(file.getSize());
        attachment.setFileType(file.getContentType());
        attachment.setFileUrl(storedFilename);
        attachment.setCreateTime(LocalDateTime.now());
        this.save(attachment);
        return attachment;
    }

    public Attachment getAttachment(Long attachmentId, Long userId) {
        Attachment attachment = this.getById(attachmentId);
        if (attachment == null) {
            throw new RuntimeException("附件不存在");
        }
        documentService.getReadableDocument(attachment.getDocumentId(), userId);
        return attachment;
    }

    public void deleteAttachment(Long attachmentId, Long userId) {
        Attachment attachment = this.getById(attachmentId);
        if (attachment == null) {
            throw new RuntimeException("附件不存在");
        }

        documentService.getEditableDocument(attachment.getDocumentId(), userId);
        this.removeById(attachmentId);
        fileUtil.deleteFile(attachment.getFileUrl(), "attachment");
    }
}
