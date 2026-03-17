package com.cinema.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cinema.common.BusinessException;
import com.cinema.common.PageResult;
import com.cinema.entity.Hall;
import com.cinema.entity.Showtime;
import com.cinema.mapper.CinemaMapper;
import com.cinema.mapper.HallMapper;
import com.cinema.mapper.MovieMapper;
import com.cinema.mapper.ShowtimeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ShowtimeService {

    @Resource
    private ShowtimeMapper showtimeMapper;

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private CinemaMapper cinemaMapper;

    @Resource
    private HallMapper hallMapper;

    public PageResult<Showtime> page(Integer pageNum, Integer pageSize, Long movieId, Long cinemaId, String status, LocalDate date) {
        Page<Showtime> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Showtime> wrapper = new LambdaQueryWrapper<Showtime>()
                .eq(movieId != null, Showtime::getMovieId, movieId)
                .eq(cinemaId != null, Showtime::getCinemaId, cinemaId)
                .eq(status != null && !status.trim().isEmpty(), Showtime::getStatus, status == null ? null : status.trim())
                .ge(date != null, Showtime::getStartTime, date == null ? null : date.atStartOfDay())
                .lt(date != null, Showtime::getStartTime, date == null ? null : date.plusDays(1).atStartOfDay())
                .orderByDesc(Showtime::getStartTime);
        Page<Showtime> resultPage = showtimeMapper.selectPage(page, wrapper);
        PageResult<Showtime> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public PageResult<Showtime> publicPage(Integer pageNum, Integer pageSize, Long movieId, Long cinemaId, LocalDate date) {
        return page(pageNum, pageSize, movieId, cinemaId, "SELLING", date);
    }

    public Showtime getById(Long id) {
        Showtime showtime = showtimeMapper.selectById(id);
        if (showtime == null) {
            throw new BusinessException("场次不存在");
        }
        return showtime;
    }

    public void save(Showtime showtime) {
        if (showtime == null || showtime.getMovieId() == null || showtime.getCinemaId() == null || showtime.getHallId() == null) {
            throw new BusinessException("场次参数不完整");
        }
        if (showtime.getStartTime() == null || showtime.getEndTime() == null || showtime.getPrice() == null) {
            throw new BusinessException("时间和票价不能为空");
        }
        if (showtime.getEndTime().isBefore(showtime.getStartTime()) || showtime.getEndTime().isEqual(showtime.getStartTime())) {
            throw new BusinessException("结束时间必须大于开始时间");
        }
        if (movieMapper.selectById(showtime.getMovieId()) == null) {
            throw new BusinessException("影片不存在");
        }
        if (cinemaMapper.selectById(showtime.getCinemaId()) == null) {
            throw new BusinessException("影院不存在");
        }
        Hall hall = hallMapper.selectById(showtime.getHallId());
        if (hall == null) {
            throw new BusinessException("影厅不存在");
        }
        showtime.setStatus(showtime.getStatus() == null ? "SELLING" : showtime.getStatus().trim());
        if (showtime.getAvailableSeats() == null || showtime.getAvailableSeats() <= 0) {
            Integer seats = hall.getTotalSeats() == null ? 0 : hall.getTotalSeats();
            showtime.setAvailableSeats(seats);
        }
        if (showtime.getId() == null) {
            showtimeMapper.insert(showtime);
        } else {
            if (showtimeMapper.selectById(showtime.getId()) == null) {
                throw new BusinessException("场次不存在");
            }
            showtimeMapper.updateById(showtime);
        }
    }

    public void deleteById(Long id) {
        showtimeMapper.deleteById(id);
    }

    public void changeAvailableSeats(Long showtimeId, int delta) {
        Showtime showtime = showtimeMapper.selectById(showtimeId);
        if (showtime == null) {
            return;
        }
        int current = showtime.getAvailableSeats() == null ? 0 : showtime.getAvailableSeats();
        int value = current + delta;
        if (value < 0) {
            throw new BusinessException("余票不足");
        }
        showtime.setAvailableSeats(value);
        if (showtime.getEndTime() != null && LocalDateTime.now().isAfter(showtime.getEndTime())) {
            showtime.setStatus("FINISHED");
        }
        showtimeMapper.updateById(showtime);
    }
}
