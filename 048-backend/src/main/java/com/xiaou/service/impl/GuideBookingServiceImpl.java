package com.xiaou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.dto.GuideBookingDTO;
import com.xiaou.entity.GuideBooking;
import com.xiaou.entity.User;
import com.xiaou.mapper.GuideBookingMapper;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.GuideBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class GuideBookingServiceImpl implements GuideBookingService {
    private final GuideBookingMapper guideBookingMapper;
    private final UserMapper userMapper;

    @Override
    public void create(Long userId, GuideBookingDTO dto) {
        GuideBooking g = new GuideBooking();
        BeanUtils.copyProperties(dto, g);
        g.setUserId(userId);
        g.setOrderNo("G" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        g.setStatus(0);
        guideBookingMapper.insert(g);
    }

    @Override
    public Page<GuideBooking> pageByUser(Long userId, int current, int size) {
        LambdaQueryWrapper<GuideBooking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GuideBooking::getUserId, userId).orderByDesc(GuideBooking::getCreateTime);
        Page<GuideBooking> page = guideBookingMapper.selectPage(new Page<>(current, size), wrapper);
        page.getRecords().forEach(this::fillInfo);
        return page;
    }

    @Override
    public Page<GuideBooking> pageByGuide(Long guideId, int current, int size) {
        LambdaQueryWrapper<GuideBooking> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GuideBooking::getGuideId, guideId).orderByDesc(GuideBooking::getCreateTime);
        Page<GuideBooking> page = guideBookingMapper.selectPage(new Page<>(current, size), wrapper);
        page.getRecords().forEach(this::fillInfo);
        return page;
    }

    @Override
    public Page<GuideBooking> pageAll(int current, int size, Integer status) {
        LambdaQueryWrapper<GuideBooking> wrapper = new LambdaQueryWrapper<>();
        if (status != null) wrapper.eq(GuideBooking::getStatus, status);
        wrapper.orderByDesc(GuideBooking::getCreateTime);
        Page<GuideBooking> page = guideBookingMapper.selectPage(new Page<>(current, size), wrapper);
        page.getRecords().forEach(this::fillInfo);
        return page;
    }

    @Override
    public void cancel(Long id) {
        GuideBooking g = new GuideBooking();
        g.setId(id);
        g.setStatus(4);
        guideBookingMapper.updateById(g);
    }

    @Override
    public void confirm(Long id) {
        GuideBooking g = new GuideBooking();
        g.setId(id);
        g.setStatus(1);
        guideBookingMapper.updateById(g);
    }

    @Override
    public void complete(Long id) {
        GuideBooking g = new GuideBooking();
        g.setId(id);
        g.setStatus(3);
        guideBookingMapper.updateById(g);
    }

    private void fillInfo(GuideBooking g) {
        User u = userMapper.selectById(g.getUserId());
        if (u != null) g.setUsername(u.getUsername());
        if (g.getGuideId() != null) {
            User guide = userMapper.selectById(g.getGuideId());
            if (guide != null) g.setGuideName(guide.getRealName());
        }
    }
}
