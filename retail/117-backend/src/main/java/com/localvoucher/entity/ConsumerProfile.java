package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("consumer_profile")
public class ConsumerProfile {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String consumerName;
    private String consumerNo;
    private String phone;
    private String levelName;
    private String registerSource;
    private String cityName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
