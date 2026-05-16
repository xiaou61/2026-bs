package com.assetrfid.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("borrow_application")
public class BorrowApplication {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String applicationNo;
    private String borrowerName;
    private String assetName;
    private Integer borrowDays;
    private String plannedReturnDate;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
