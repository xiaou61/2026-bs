package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.entity.Showtime;
import com.ticket.mapper.ShowtimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeMapper showtimeMapper;

    public Page<Showtime> listShowtimes(Integer pageNum, Integer pageSize, Long movieId, Long cinemaId, String date) {
        Page<Showtime> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Showtime> wrapper = new QueryWrapper<>();
        if (movieId != null) {
            wrapper.eq("movie_id", movieId);
        }
        if (cinemaId != null) {
            wrapper.eq("cinema_id", cinemaId);
        }
        if (date != null && !date.isEmpty()) {
            wrapper.likeRight("start_time", date);
        }
        wrapper.orderByAsc("start_time");
        return showtimeMapper.selectPage(page, wrapper);
    }

    public Showtime getShowtimeById(Long id) {
        Showtime showtime = showtimeMapper.selectById(id);
        if (showtime == null) {
            throw new BusinessException("场次不存在");
        }
        return showtime;
    }

    public void addShowtime(Showtime showtime) {
        showtimeMapper.insert(showtime);
    }

    public void updateShowtime(Showtime showtime) {
        showtimeMapper.updateById(showtime);
    }

    public void deleteShowtime(Long id) {
        showtimeMapper.deleteById(id);
    }

    public void updateAvailableSeats(Long showtimeId, Integer change) {
        Showtime showtime = showtimeMapper.selectById(showtimeId);
        if (showtime != null) {
            showtime.setAvailableSeats(showtime.getAvailableSeats() + change);
            showtimeMapper.updateById(showtime);
        }
    }
}
