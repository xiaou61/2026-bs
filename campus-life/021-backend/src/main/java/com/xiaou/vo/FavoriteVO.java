package com.xiaou.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FavoriteVO {

    private Long productId;
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
    private String sellerName;
    private String sellerAvatar;
    private Integer sellerCreditScore;
    private String categoryName;
    private LocalDateTime favoriteTime;

    public List<String> getImageList() {
        if (images == null || images.isEmpty()) {
            return List.of();
        }
        return List.of(images.split(","));
    }
}
