package com.noisemonitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("noise_source")
public class NoiseSource {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String sourceNo;
    private String sourceName;
    private String responsibleUnit;
    private String sourceType;
    private String contactName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}






