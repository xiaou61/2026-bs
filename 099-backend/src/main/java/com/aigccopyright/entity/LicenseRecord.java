package com.aigccopyright.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("license_record")
public class LicenseRecord {
    private Long id;
    private Long registerId;
    private String licensee;
    private String purpose;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
