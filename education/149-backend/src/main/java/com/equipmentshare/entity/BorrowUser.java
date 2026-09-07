package com.equipmentshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("borrow_user")
public class BorrowUser {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String borrowerNo;
    private String borrowerName;
    private String className;
    private String phoneNumber;
    private String registerTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








