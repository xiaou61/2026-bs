package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.entity.Hall;
import com.ticket.mapper.HallMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HallService {

    @Autowired
    private HallMapper hallMapper;

    public Page<Hall> listHalls(Integer pageNum, Integer pageSize, Long cinemaId) {
        Page<Hall> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Hall> wrapper = new QueryWrapper<>();
        if (cinemaId != null) {
            wrapper.eq("cinema_id", cinemaId);
        }
        wrapper.orderByAsc("cinema_id").orderByAsc("name");
        return hallMapper.selectPage(page, wrapper);
    }

    public Hall getHallById(Long id) {
        Hall hall = hallMapper.selectById(id);
        if (hall == null) {
            throw new BusinessException("影厅不存在");
        }
        return hall;
    }

    public void addHall(Hall hall) {
        hallMapper.insert(hall);
    }

    public void updateHall(Hall hall) {
        hallMapper.updateById(hall);
    }

    public void deleteHall(Long id) {
        hallMapper.deleteById(id);
    }
}
