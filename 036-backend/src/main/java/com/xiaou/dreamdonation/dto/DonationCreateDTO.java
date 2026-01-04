package com.xiaou.dreamdonation.dto;

import com.xiaou.dreamdonation.entity.Donation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class DonationCreateDTO {
    @NotNull(message = "项目ID不能为空")
    private Long projectId;

    @NotNull(message = "捐赠金额不能为空")
    private BigDecimal amount;

    private String message;

    private Boolean anonymous = false;

    @NotNull(message = "支付方式不能为空")
    private Donation.PaymentMethod paymentMethod;
}
