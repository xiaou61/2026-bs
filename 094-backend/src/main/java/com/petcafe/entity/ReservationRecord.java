package com.petcafe.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("reservation_record")
public class ReservationRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String reservationNo;
    private Long userId;
    private Long shopId;
    private Long seatId;
    private String customerName;
    private String customerPhone;
    private LocalDate visitDate;
    private String visitTime;
    private Integer peopleCount;
    private String petCompanion;
    private String remark;
    private String status;
    private String managerRemark;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(exist = false)
    private String username;
    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String shopName;
    @TableField(exist = false)
    private String seatNo;
}
