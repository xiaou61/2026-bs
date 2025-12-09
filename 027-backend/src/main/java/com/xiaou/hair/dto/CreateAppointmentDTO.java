package com.xiaou.hair.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 创建预约DTO
 */
@Data
public class CreateAppointmentDTO {
    
    @NotNull(message = "门店不能为空")
    private Long storeId;
    
    @NotNull(message = "理发师不能为空")
    private Long hairdresserId;
    
    @NotNull(message = "服务项目不能为空")
    private Long serviceId;
    
    @NotNull(message = "预约日期不能为空")
    private LocalDate appointmentDate;
    
    @NotNull(message = "预约时间不能为空")
    private LocalTime appointmentTime;
}
