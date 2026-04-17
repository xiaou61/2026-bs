package com.xiaou.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDetailVO {

    private Long id;
    private String orderNo;
    private Long productId;
    private String productTitle;
    private String productImages;
    private Long sellerId;
    private String sellerName;
    private String sellerAvatar;
    private Integer sellerCreditScore;
    private Long buyerId;
    private String buyerName;
    private String buyerAvatar;
    private Integer buyerCreditScore;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer isBargain;
    private String status;
    private Integer buyerRating;
    private String buyerComment;
    private Integer sellerRating;
    private String sellerComment;
    private LocalDateTime createTime;
    private LocalDateTime completeTime;
    private LocalDateTime cancelTime;

    public List<String> getImageList() {
        if (productImages == null || productImages.isEmpty()) {
            return List.of();
        }
        return List.of(productImages.split(","));
    }
}
