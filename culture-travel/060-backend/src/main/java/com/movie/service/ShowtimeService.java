package com.movie.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movie.entity.Hall;
import com.movie.entity.Showtime;
import com.movie.mapper.HallMapper;
import com.movie.mapper.ShowtimeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ShowtimeService {

    @Resource
    private ShowtimeMapper showtimeMapper;

    @Resource
    private HallMapper hallMapper;

    public PageInfo<Showtime> getPage(Integer pageNum, Integer pageSize, Long movieId, Long cinemaId, String showDate) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(showtimeMapper.selectPage(movieId, cinemaId, showDate));
    }

    public List<Showtime> getByMovieId(Long movieId) {
        return showtimeMapper.selectByMovieId(movieId);
    }

    public Showtime getById(Long id) {
        return showtimeMapper.selectById(id);
    }

    public void add(Showtime showtime) {
        Hall hall = hallMapper.selectById(showtime.getHallId());
        if (hall != null) {
            showtime.setAvailableSeats(hall.getSeatRows() * hall.getSeatCols());
        }
        showtimeMapper.insert(showtime);
    }

    public void update(Showtime showtime) {
        showtimeMapper.update(showtime);
    }

    public void delete(Long id) {
        showtimeMapper.deleteById(id);
    }
}
