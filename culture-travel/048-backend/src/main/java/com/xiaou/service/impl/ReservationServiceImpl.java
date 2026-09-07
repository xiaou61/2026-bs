package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.ReservationDTO;
import com.xiaou.entity.Exhibition;
import com.xiaou.entity.Reservation;
import com.xiaou.entity.User;
import com.xiaou.mapper.ExhibitionMapper;
import com.xiaou.mapper.ReservationMapper;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationMapper reservationMapper;
    private final ExhibitionMapper exhibitionMapper;
    private final UserMapper userMapper;

    @Override
    public void create(Long userId, ReservationDTO dto) {
        Reservation r = new Reservation();
        BeanUtils.copyProperties(dto, r);
        r.setUserId(userId);
        r.setOrderNo("R" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        r.setStatus(0);
        reservationMapper.insert(r);
    }

    @Override
    public Page<Reservation> pageByUser(Long userId, int current, int size) {
        LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Reservation::getUserId, userId).orderByDesc(Reservation::getCreateTime);
        Page<Reservation> page = reservationMapper.selectPage(new Page<>(current, size), wrapper);
        page.getRecords().forEach(this::fillInfo);
        return page;
    }

    @Override
    public Page<Reservation> pageAll(int current, int size, Integer status) {
        LambdaQueryWrapper<Reservation> wrapper = new LambdaQueryWrapper<>();
        if (status != null) wrapper.eq(Reservation::getStatus, status);
        wrapper.orderByDesc(Reservation::getCreateTime);
        Page<Reservation> page = reservationMapper.selectPage(new Page<>(current, size), wrapper);
        page.getRecords().forEach(this::fillInfo);
        return page;
    }

    @Override
    public void cancel(Long id) {
        Reservation r = new Reservation();
        r.setId(id);
        r.setStatus(3);
        reservationMapper.updateById(r);
    }

    @Override
    public void confirm(Long id) {
        Reservation r = new Reservation();
        r.setId(id);
        r.setStatus(1);
        reservationMapper.updateById(r);
    }

    @Override
    public void complete(Long id) {
        Reservation r = new Reservation();
        r.setId(id);
        r.setStatus(2);
        reservationMapper.updateById(r);
    }

    private void fillInfo(Reservation r) {
        if (r.getExhibitionId() != null) {
            Exhibition e = exhibitionMapper.selectById(r.getExhibitionId());
            if (e != null) r.setExhibitionTitle(e.getTitle());
        }
        User u = userMapper.selectById(r.getUserId());
        if (u != null) r.setUsername(u.getUsername());
    }
}
