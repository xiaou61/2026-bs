package com.xiaou.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReservationDTO {
    private Long exhibitionId;
    private LocalDate visitDate;
    private String timeSlot;
    private Integer visitorCount;
    private String contactName;
    private String contactPhone;
    private BigDecimal totalPrice;
    private String remark;
}
