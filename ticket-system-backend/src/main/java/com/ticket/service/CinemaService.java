package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.entity.Cinema;
import com.ticket.mapper.CinemaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {

    @Autowired
    private CinemaMapper cinemaMapper;

    public Page<Cinema> listCinemas(Integer pageNum, Integer pageSize, String name) {
        Page<Cinema> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Cinema> wrapper = new QueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like("name", name);
        }
        wrapper.orderByDesc("create_time");
        return cinemaMapper.selectPage(page, wrapper);
    }

    public Cinema getCinemaById(Long id) {
        Cinema cinema = cinemaMapper.selectById(id);
        if (cinema == null) {
            throw new BusinessException("影院不存在");
        }
        return cinema;
    }

    public void addCinema(Cinema cinema) {
        cinemaMapper.insert(cinema);
    }

    public void updateCinema(Cinema cinema) {
        cinemaMapper.updateById(cinema);
    }

    public void deleteCinema(Long id) {
        cinemaMapper.deleteById(id);
    }
}
