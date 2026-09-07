package com.xiaou.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class GuideBookingDTO {
    private Long guideId;
    private LocalDate visitDate;
    private LocalTime startTime;
    private Integer duration;
    private Integer visitorCount;
    private String language;
    private BigDecimal price;
    private String remark;
}
