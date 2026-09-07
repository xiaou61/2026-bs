package com.equipmentshare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("return_confirmation")
public class ReturnConfirmation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String confirmNo;
    private String equipmentName;
    private String returnerName;
    private String returnStatus;
    private String confirmTime;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}








