package com.eldercare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("check_package_item")
public class CheckPackageItem {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long packageId;
    private Long itemId;
    private Integer sort;
}
