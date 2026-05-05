package com.crossborder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("merchant_store")
public class MerchantStore {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String storeName;
    private String storeCode;
    private String platformName;
    private String companyName;
    private String countryRegion;
    private String contactName;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
