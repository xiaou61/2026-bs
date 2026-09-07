package com.localvoucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("store_branch")
public class StoreBranch {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String branchName;
    private String branchNo;
    private String merchantName;
    private String cityName;
    private String businessDistrict;
    private String addressText;
    private String status;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
