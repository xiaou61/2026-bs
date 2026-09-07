package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.common.PageResult;
import com.ticket.entity.Cinema;
import com.ticket.mapper.CinemaMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CinemaService {

    @Resource
    private CinemaMapper cinemaMapper;

    public PageResult<Cinema> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        Page<Cinema> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Cinema> wrapper = new LambdaQueryWrapper<Cinema>()
                .like(StringUtils.hasText(name), Cinema::getName, name == null ? null : name.trim())
                .eq(status != null, Cinema::getStatus, status)
                .orderByDesc(Cinema::getId);
        Page<Cinema> resultPage = cinemaMapper.selectPage(page, wrapper);
        PageResult<Cinema> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<Cinema> publicList() {
        return cinemaMapper.selectList(new LambdaQueryWrapper<Cinema>()
                .eq(Cinema::getStatus, 1)
                .orderByDesc(Cinema::getId));
    }

    public Cinema getById(Long id) {
        Cinema cinema = cinemaMapper.selectById(id);
        if (cinema == null) {
            throw new BusinessException("影院不存在");
        }
        return cinema;
    }

    public void save(Cinema cinema) {
        if (cinema == null || !StringUtils.hasText(cinema.getName())) {
            throw new BusinessException("影院名称不能为空");
        }
        cinema.setName(cinema.getName().trim());
        cinema.setAddress(StringUtils.hasText(cinema.getAddress()) ? cinema.getAddress().trim() : "");
        cinema.setPhone(StringUtils.hasText(cinema.getPhone()) ? cinema.getPhone().trim() : "");
        cinema.setBusinessHours(StringUtils.hasText(cinema.getBusinessHours()) ? cinema.getBusinessHours().trim() : "09:00-23:00");
        cinema.setFacilities(StringUtils.hasText(cinema.getFacilities()) ? cinema.getFacilities().trim() : "");
        cinema.setStatus(cinema.getStatus() == null ? 1 : cinema.getStatus());
        if (cinema.getId() == null) {
            cinemaMapper.insert(cinema);
        } else {
            if (cinemaMapper.selectById(cinema.getId()) == null) {
                throw new BusinessException("影院不存在");
            }
            cinemaMapper.updateById(cinema);
        }
    }

    public void deleteById(Long id) {
        cinemaMapper.deleteById(id);
    }
}
