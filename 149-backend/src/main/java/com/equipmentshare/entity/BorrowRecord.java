package com.equipmentshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("borrow_record")
public class BorrowRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String borrowNo;
    private String equipmentName;
    private String borrowerName;
    private String borrowTime;
    private String borrowPurpose;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








