package com.parkingguide.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("guidance_screen")
public class GuidanceScreen {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String screenNo;
    private String lotName;
    private String positionName;
    private String displayText;
    private String refreshTime;
    private String managerName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
