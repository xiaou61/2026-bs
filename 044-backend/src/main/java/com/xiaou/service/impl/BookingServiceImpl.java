package com.xiaou.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.common.BusinessException;
import com.xiaou.dto.BookingDTO;
import com.xiaou.entity.Booking;
import com.xiaou.entity.Homestay;
import com.xiaou.entity.RoomType;
import com.xiaou.mapper.BookingMapper;
import com.xiaou.mapper.HomestayMapper;
import com.xiaou.mapper.RoomTypeMapper;
import com.xiaou.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl extends ServiceImpl<BookingMapper, Booking> implements BookingService {

    private final RoomTypeMapper roomTypeMapper;
    private final HomestayMapper homestayMapper;

    @Override
    public void createBooking(Long userId, BookingDTO dto) {
        if (dto.getCheckInDate().isBefore(LocalDate.now())) {
            throw new BusinessException("入住日期不能早于今天");
        }
        if (!dto.getCheckOutDate().isAfter(dto.getCheckInDate())) {
            throw new BusinessException("离店日期必须晚于入住日期");
        }
        RoomType roomType = roomTypeMapper.selectById(dto.getRoomTypeId());
        if (roomType == null || roomType.getStatus() != 1) {
            throw new BusinessException("房型不存在或已下架");
        }
        long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        BigDecimal totalPrice = roomType.getPrice().multiply(BigDecimal.valueOf(nights));

        Booking booking = new Booking();
        booking.setOrderNo("B" + IdUtil.getSnowflakeNextIdStr());
        booking.setUserId(userId);
        booking.setHomestayId(dto.getHomestayId());
        booking.setRoomTypeId(dto.getRoomTypeId());
        booking.setCheckInDate(dto.getCheckInDate());
        booking.setCheckOutDate(dto.getCheckOutDate());
        booking.setNights((int) nights);
        booking.setGuests(dto.getGuests() != null ? dto.getGuests() : 1);
        booking.setTotalPrice(totalPrice);
        booking.setContactName(dto.getContactName());
        booking.setContactPhone(dto.getContactPhone());
        booking.setRemark(dto.getRemark());
        booking.setStatus(1);  // 待确认
        save(booking);
    }

    @Override
    public void cancelBooking(Long userId, Long id, String reason) {
        Booking booking = getById(id);
        if (booking == null || !booking.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (booking.getStatus() >= 3) {
            throw new BusinessException("该订单无法取消");
        }
        booking.setStatus(5);
        booking.setCancelReason(reason);
        updateById(booking);
    }

    @Override
    public void confirmBooking(Long hostId, Long id) {
        Booking booking = getById(id);
        if (booking == null) {
            throw new BusinessException("订单不存在");
        }
        Homestay homestay = homestayMapper.selectById(booking.getHomestayId());
        if (homestay == null || !homestay.getHostId().equals(hostId)) {
            throw new BusinessException("无权操作此订单");
        }
        if (booking.getStatus() != 1) {
            throw new BusinessException("只能确认待确认的订单");
        }
        booking.setStatus(2);
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
    public IPage<Booking> pageByHost(Long hostId, Integer current, Integer size, Integer status) {
        // 先查房东的所有民宿ID
        LambdaQueryWrapper<Homestay> homestayWrapper = new LambdaQueryWrapper<>();
        homestayWrapper.eq(Homestay::getHostId, hostId).select(Homestay::getId);
        var homestays = homestayMapper.selectList(homestayWrapper);
        if (homestays.isEmpty()) {
            return new Page<>(current, size);
        }
        var homestayIds = homestays.stream().map(Homestay::getId).toList();

        LambdaQueryWrapper<Booking> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Booking::getHomestayId, homestayIds);
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
            throw new BusinessException("订单不存在");
        }
        return booking;
    }
}
