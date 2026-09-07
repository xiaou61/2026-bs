package com.agritrace.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("trace_node")
public class TraceNode {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String nodeName;
    private String nodeCode;
    private String nodeType;
    private String regionName;
    private String contactName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
