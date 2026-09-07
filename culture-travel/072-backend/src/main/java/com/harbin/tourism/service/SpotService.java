package com.harbin.tourism.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.entity.ScenicSpot;
import com.harbin.tourism.entity.TicketType;
import com.harbin.tourism.mapper.ScenicSpotMapper;
import com.harbin.tourism.mapper.TicketTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpotService {

    @Autowired
    private ScenicSpotMapper spotMapper;

    @Autowired
    private TicketTypeMapper ticketTypeMapper;

    public Page<ScenicSpot> page(Integer pageNum, Integer pageSize, String keyword, String category) {
        LambdaQueryWrapper<ScenicSpot> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(ScenicSpot::getName, keyword);
        }
        if (StrUtil.isNotBlank(category)) {
            wrapper.eq(ScenicSpot::getCategory, category);
        }
        wrapper.eq(ScenicSpot::getStatus, 1);
        wrapper.orderByDesc(ScenicSpot::getViewCount);
        return spotMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Map<String, Object> detail(Long id) {
        ScenicSpot spot = spotMapper.selectById(id);
        if (spot != null) {
            spot.setViewCount(spot.getViewCount() + 1);
            spotMapper.updateById(spot);
        }
        List<TicketType> ticketTypes = ticketTypeMapper.selectList(
                new LambdaQueryWrapper<TicketType>()
                        .eq(TicketType::getSpotId, id)
                        .eq(TicketType::getStatus, 1)
        );
        Map<String, Object> result = new HashMap<>();
        result.put("spot", spot);
        result.put("ticketTypes", ticketTypes);
        return result;
    }

    public ScenicSpot getById(Long id) {
        return spotMapper.selectById(id);
    }

    public void save(ScenicSpot spot) {
        spot.setViewCount(0);
        spot.setStatus(1);
        spotMapper.insert(spot);
    }

    public void update(ScenicSpot spot) {
        spotMapper.updateById(spot);
    }

    public void delete(Long id) {
        spotMapper.deleteById(id);
    }

    public List<ScenicSpot> top10() {
        return spotMapper.selectList(new LambdaQueryWrapper<ScenicSpot>()
                .eq(ScenicSpot::getStatus, 1)
                .orderByDesc(ScenicSpot::getViewCount)
                .last("LIMIT 10"));
    }

    public long count() {
        return spotMapper.selectCount(new LambdaQueryWrapper<ScenicSpot>()
                .eq(ScenicSpot::getStatus, 1));
    }

    public List<ScenicSpot> listAll() {
        return spotMapper.selectList(new LambdaQueryWrapper<ScenicSpot>()
                .eq(ScenicSpot::getStatus, 1));
    }
}
