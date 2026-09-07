package com.hospital.mapper;

import com.hospital.entity.PaymentOrder;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentOrderMapper {
    List<PaymentOrder> selectPage(@Param("keyword") String keyword, @Param("status") Integer status);

    List<PaymentOrder> selectMyPage(@Param("userId") Long userId, @Param("status") Integer status);

    PaymentOrder selectById(@Param("id") Long id);

    PaymentOrder selectByAppointmentId(@Param("appointmentId") Long appointmentId);

    void insert(PaymentOrder entity);

    void updatePaid(@Param("id") Long id,
                    @Param("status") Integer status,
                    @Param("paymentMethod") String paymentMethod,
                    @Param("payTime") LocalDateTime payTime);

    void updateStatus(@Param("id") Long id, @Param("status") Integer status);

    java.math.BigDecimal sumPaidAmount();
}
