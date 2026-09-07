package com.movie.service;

import com.movie.mapper.MovieMapper;
import com.movie.mapper.TicketOrderMapper;
import com.movie.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private TicketOrderMapper ticketOrderMapper;

    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("userCount", userMapper.count());
        stats.put("movieCount", movieMapper.count());
        stats.put("orderCount", ticketOrderMapper.count());
        stats.put("totalIncome", ticketOrderMapper.sumTotalPrice());
        return stats;
    }

    public List<Map<String, Object>> getCategoryStats() {
        return movieMapper.selectCategoryStats();
    }

    public List<Map<String, Object>> getOrderTrend() {
        return ticketOrderMapper.selectOrderTrend();
    }

    public List<Map<String, Object>> getBoxOfficeRank() {
        return ticketOrderMapper.selectBoxOfficeRank();
    }
}
