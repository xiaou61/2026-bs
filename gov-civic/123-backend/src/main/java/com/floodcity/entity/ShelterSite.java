package com.floodcity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("shelter_site")
public class ShelterSite {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String siteNo;
    private String siteName;
    private String addressName;
    private Integer capacityCount;
    private String managerName;
    private String contactPhone;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
