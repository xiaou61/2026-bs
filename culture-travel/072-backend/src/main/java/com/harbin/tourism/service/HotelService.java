package com.harbin.tourism.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.entity.Hotel;
import com.harbin.tourism.mapper.HotelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    public Page<Hotel> page(Integer pageNum, Integer pageSize, String keyword, String type) {
        LambdaQueryWrapper<Hotel> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(Hotel::getName, keyword);
        }
        if (StrUtil.isNotBlank(type)) {
            wrapper.eq(Hotel::getType, type);
        }
        wrapper.eq(Hotel::getStatus, 1);
        wrapper.orderByDesc(Hotel::getRating);
        return hotelMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Hotel getById(Long id) {
        return hotelMapper.selectById(id);
    }

    public void save(Hotel hotel) {
        hotel.setStatus(1);
        hotelMapper.insert(hotel);
    }

    public void update(Hotel hotel) {
        hotelMapper.updateById(hotel);
    }

    public void delete(Long id) {
        hotelMapper.deleteById(id);
    }

    public List<Hotel> listAll() {
        return hotelMapper.selectList(new LambdaQueryWrapper<Hotel>()
                .eq(Hotel::getStatus, 1));
    }
}
