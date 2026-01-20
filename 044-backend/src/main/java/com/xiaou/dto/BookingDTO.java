package com.xiaou.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingDTO {
    @NotNull(message = "民宿ID不能为空")
    private Long homestayId;
    @NotNull(message = "房型ID不能为空")
    private Long roomTypeId;
    @NotNull(message = "入住日期不能为空")
    private LocalDate checkInDate;
    @NotNull(message = "离店日期不能为空")
    private LocalDate checkOutDate;
    private Integer guests;
    private String contactName;
    private String contactPhone;
    private String remark;
}
