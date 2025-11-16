package com.xiaou.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDetailVO {

    private Long id;
    private Long sellerId;
    private Long categoryId;
    private String title;
    private String description;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private String condition;
    private String images;
    private String status;
    private Integer viewCount;
    private Integer favoriteCount;
    private LocalDateTime createTime;

    // 卖家详细信息
    private String sellerName;
    private String sellerAvatar;
    private Integer sellerCreditScore;
    private String sellerCollege;
    private String sellerDorm;
    private String sellerPhone;
    private String categoryName;

    // 图片列表转换
    public List<String> getImageList() {
        if (images == null || images.isEmpty()) {
            return List.of();
        }
        return List.of(images.split(","));
    }
}