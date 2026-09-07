package com.xiaou.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("order_info")
public class OrderInfo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("order_no")
    private String orderNo;

    @TableField("product_id")
    private Long productId;

    @TableField("seller_id")
    private Long sellerId;

    @TableField("buyer_id")
    private Long buyerId;

    @TableField("price")
    private BigDecimal price;

    @TableField("original_price")
    private BigDecimal originalPrice;

    @TableField("is_bargain")
    private Integer isBargain;

    @TableField("status")
    private String status;

    @TableField("buyer_rating")
    private Integer buyerRating;

    @TableField("buyer_comment")
    private String buyerComment;

    @TableField("seller_rating")
    private Integer sellerRating;

    @TableField("seller_comment")
    private String sellerComment;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField("complete_time")
    private LocalDateTime completeTime;

    @TableField("cancel_time")
    private LocalDateTime cancelTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}