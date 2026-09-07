package com.apitestcase.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("document_snapshot")
public class DocumentSnapshot {
    @TableId(type = IdType.AUTO)
private Long id;
private Long projectId;
private String snapshotNo;
private String title;
private String versionNo;
private String contentSummary;
private String publisher;
private String status;
private String publishedAt;
private LocalDateTime createdTime;
private LocalDateTime updatedTime;
}
