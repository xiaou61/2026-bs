package com.xiaou.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderVO {

    private Long id;
    private String orderNo;
    private Long productId;
    private String productTitle;
    private String productImages;
    private Long sellerId;
    private String sellerName;
    private Long buyerId;
    private String buyerName;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer isBargain;
    private String status;
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
