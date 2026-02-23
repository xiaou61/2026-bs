package com.ticket.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.dto.OrderCreateDTO;
import com.ticket.entity.*;
import com.ticket.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SeatService seatService;

    @Autowired
    private ShowtimeService showtimeService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private HallService hallService;

    @Autowired
    private UserService userService;

    @Transactional
    public Map<String, Object> createOrder(OrderCreateDTO dto, Long userId) {
        List<Seat> seats = seatService.getSeatsByIds(dto.getSeatIds());
        if (seats.isEmpty()) {
            throw new BusinessException("座位不存在");
        }

        if (!seatService.lockSeats(dto.getSeatIds(), userId)) {
            throw new BusinessException("座位已被锁定或已售");
        }

        Showtime showtime = showtimeService.getShowtimeById(dto.getShowtimeId());
        Movie movie = movieService.getMovieById(showtime.getMovieId());
        Cinema cinema = cinemaService.getCinemaById(showtime.getCinemaId());
        Hall hall = hallService.getHallById(showtime.getHallId());

        BigDecimal totalAmount = seats.stream()
                .map(Seat::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String seatInfo = seats.stream()
                .map(s -> s.getRowNum() + "排" + s.getColNum() + "座")
                .collect(Collectors.joining(","));

        Order order = new Order();
        order.setOrderNo("ORD" + IdUtil.getSnowflakeNextIdStr());
        order.setUserId(userId);
        order.setShowtimeId(showtime.getId());
        order.setMovieTitle(movie.getTitle());
        order.setCinemaName(cinema.getName());
        order.setHallName(hall.getName());
        order.setShowTime(showtime.getStartTime());
        order.setSeatInfo(seatInfo);
        order.setTotalAmount(totalAmount);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setPayAmount(totalAmount);
        order.setCouponId(dto.getCouponId());
        order.setStatus("unpaid");

        orderMapper.insert(order);

        Map<String, Object> result = new HashMap<>();
        result.put("orderId", order.getId());
        result.put("orderNo", order.getOrderNo());
        return result;
    }

    public Page<Order> listOrders(Integer pageNum, Integer pageSize, String status, Long userId) {
        Page<Order> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        wrapper.orderByDesc("create_time");
        return orderMapper.selectPage(page, wrapper);
    }

    public Order getOrderById(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        return order;
    }

    @Transactional
    public void cancelOrder(Long orderId, Long userId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权操作");
        }

        if (!"unpaid".equals(order.getStatus())) {
            throw new BusinessException("订单状态不允许取消");
        }

        order.setStatus("canceled");
        orderMapper.updateById(order);
    }

    @Transactional
    public void payOrder(Long orderId, Long userId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }

        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权操作");
        }

        if (!"unpaid".equals(order.getStatus())) {
            throw new BusinessException("订单已支付或已取消");
        }

        User user = userService.getUserInfo(userId);
        if (user.getBalance().compareTo(order.getPayAmount()) < 0) {
            throw new BusinessException("余额不足");
        }

        user.setBalance(user.getBalance().subtract(order.getPayAmount()));
        userService.updateUser(user);

        order.setStatus("paid");
        orderMapper.updateById(order);
    }
}
