package com.xiaou.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ReservationDTO {
    private Long shopId;
    private Long roomId;
    private Long scriptId;
    private String reservationDate;
    private String timeSlot;
    private Integer playerCount;
    private String contactName;
    private String contactPhone;
    private BigDecimal totalPrice;
    private String remark;
}
