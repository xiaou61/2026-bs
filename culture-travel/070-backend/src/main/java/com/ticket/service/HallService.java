package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.common.PageResult;
import com.ticket.entity.Hall;
import com.ticket.mapper.CinemaMapper;
import com.ticket.mapper.HallMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HallService {

    @Resource
    private HallMapper hallMapper;

    @Resource
    private CinemaMapper cinemaMapper;

    public PageResult<Hall> page(Integer pageNum, Integer pageSize, Long cinemaId, Integer status) {
        Page<Hall> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Hall> wrapper = new LambdaQueryWrapper<Hall>()
                .eq(cinemaId != null, Hall::getCinemaId, cinemaId)
                .eq(status != null, Hall::getStatus, status)
                .orderByDesc(Hall::getId);
        Page<Hall> resultPage = hallMapper.selectPage(page, wrapper);
        PageResult<Hall> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<Hall> listByCinema(Long cinemaId) {
        return hallMapper.selectList(new LambdaQueryWrapper<Hall>()
                .eq(Hall::getCinemaId, cinemaId)
                .eq(Hall::getStatus, 1)
                .orderByAsc(Hall::getId));
    }

    public Hall getById(Long id) {
        Hall hall = hallMapper.selectById(id);
        if (hall == null) {
            throw new BusinessException("影厅不存在");
        }
        return hall;
    }

    public void save(Hall hall) {
        if (hall == null || hall.getCinemaId() == null || !StringUtils.hasText(hall.getName())) {
            throw new BusinessException("影厅参数不完整");
        }
        if (cinemaMapper.selectById(hall.getCinemaId()) == null) {
            throw new BusinessException("影院不存在");
        }
        hall.setName(hall.getName().trim());
        hall.setType(StringUtils.hasText(hall.getType()) ? hall.getType().trim() : "NORMAL");
        hall.setSeatRows(hall.getSeatRows() == null || hall.getSeatRows() <= 0 ? 8 : hall.getSeatRows());
        hall.setSeatCols(hall.getSeatCols() == null || hall.getSeatCols() <= 0 ? 10 : hall.getSeatCols());
        hall.setTotalSeats(hall.getTotalSeats() == null || hall.getTotalSeats() <= 0 ? hall.getSeatRows() * hall.getSeatCols() : hall.getTotalSeats());
        hall.setSeatLayout(StringUtils.hasText(hall.getSeatLayout()) ? hall.getSeatLayout().trim() : "");
        hall.setStatus(hall.getStatus() == null ? 1 : hall.getStatus());
        if (hall.getId() == null) {
            hallMapper.insert(hall);
        } else {
            if (hallMapper.selectById(hall.getId()) == null) {
                throw new BusinessException("影厅不存在");
            }
            hallMapper.updateById(hall);
        }
    }

    public void deleteById(Long id) {
        hallMapper.deleteById(id);
    }
}
