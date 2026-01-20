package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.dto.BookingDTO;
import com.xiaou.entity.Booking;
import com.xiaou.entity.ProviderServiceEntity;
import com.xiaou.mapper.BookingMapper;
import com.xiaou.mapper.ProviderServiceMapper;
import com.xiaou.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking> implements BookingService {

    private final ProviderServiceMapper providerServiceMapper;

    @Override
    public void createBooking(Long userId, BookingDTO dto) {
        if (dto.getStartDate().isBefore(LocalDate.now())) {
            throw new BusinessException("开始日期不能早于今天");
        }
        if (dto.getEndDate().isBefore(dto.getStartDate())) {
            throw new BusinessException("结束日期不能早于开始日期");
        }
        ProviderServiceEntity service = providerServiceMapper.selectById(dto.getServiceId());
        if (service == null) {
            throw new BusinessException("服务不存在");
        }
        long days = ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()) + 1;
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setPetId(dto.getPetId());
        booking.setProviderId(dto.getProviderId());
        booking.setServiceId(dto.getServiceId());
        booking.setStartDate(dto.getStartDate());
        booking.setEndDate(dto.getEndDate());
        booking.setTotalPrice(service.getPrice().multiply(java.math.BigDecimal.valueOf(days)));
        booking.setStatus(0);
        booking.setRemark(dto.getRemark());
        save(booking);
    }

    @Override
    public void cancelBooking(Long userId, Long id) {
        Booking booking = getById(id);
        if (booking == null || !booking.getUserId().equals(userId)) {
            throw new BusinessException("预约不存在");
        }
        if (booking.getStatus() != 0) {
            throw new BusinessException("只能取消待确认的预约");
        }
        booking.setStatus(4);
        updateById(booking);
    }

    @Override
    public IPage<Booking> pageByUser(Long userId, Integer current, Integer size, Integer status) {
        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Booking::getUserId, userId);
        if (status != null) {
            wrapper.eq(Booking::getStatus, status);
        }
        wrapper.orderByDesc(Booking::getCreateTime);
        return page(new Page<>(current, size), wrapper);
    }

    @Override
    public Booking detail(Long userId, Long id) {
        Booking booking = getById(id);
        if (booking == null || !booking.getUserId().equals(userId)) {
            throw new BusinessException("预约不存在");
        }
        return booking;
    }
}
