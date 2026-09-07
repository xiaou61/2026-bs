package com.ticket.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.common.PageResult;
import com.ticket.dto.OrderCreateDTO;
import com.ticket.entity.Cinema;
import com.ticket.entity.Coupon;
import com.ticket.entity.Hall;
import com.ticket.entity.Seat;
import com.ticket.entity.Showtime;
import com.ticket.entity.Ticket;
import com.ticket.entity.TicketOrder;
import com.ticket.entity.UserCoupon;
import com.ticket.entity.Movie;
import com.ticket.mapper.CinemaMapper;
import com.ticket.mapper.HallMapper;
import com.ticket.mapper.MovieMapper;
import com.ticket.mapper.ShowtimeMapper;
import com.ticket.mapper.TicketMapper;
import com.ticket.mapper.TicketOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    public static final String STATUS_WAIT_PAY = "WAIT_PAY";
    public static final String STATUS_PAID = "PAID";
    public static final String STATUS_CANCELED = "CANCELED";
    public static final String STATUS_REFUNDED = "REFUNDED";
    public static final String STATUS_FINISHED = "FINISHED";

    @Resource
    private TicketOrderMapper ticketOrderMapper;

    @Resource
    private ShowtimeMapper showtimeMapper;

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private CinemaMapper cinemaMapper;

    @Resource
    private HallMapper hallMapper;

    @Resource
    private SeatService seatService;

    @Resource
    private CouponService couponService;

    @Resource
    private TicketMapper ticketMapper;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(Long userId, OrderCreateDTO dto) {
        if (dto == null || dto.getShowtimeId() == null || dto.getSeatIds() == null || dto.getSeatIds().isEmpty()) {
            throw new BusinessException("订单参数不完整");
        }
        Showtime showtime = showtimeMapper.selectById(dto.getShowtimeId());
        if (showtime == null) {
            throw new BusinessException("场次不存在");
        }
        List<Seat> seats = seatService.getSeats(dto.getShowtimeId(), dto.getSeatIds());
        for (Seat seat : seats) {
            if (!seatService.isLockedByUser(userId, dto.getShowtimeId(), seat.getId())) {
                throw new BusinessException("请先锁定座位后再下单");
            }
        }
        BigDecimal totalAmount = seats.stream()
                .map(seat -> seat.getPrice() == null ? showtime.getPrice() : seat.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discountAmount = BigDecimal.ZERO;
        Long userCouponId = dto.getCouponId();
        if (userCouponId != null) {
            UserCoupon userCoupon = couponService.getUserCouponForUse(userId, userCouponId, totalAmount);
            Coupon coupon = couponService.getCouponById(userCoupon.getCouponId());
            discountAmount = couponService.calcDiscount(coupon, totalAmount);
        }
        BigDecimal payAmount = totalAmount.subtract(discountAmount);
        if (payAmount.compareTo(BigDecimal.ZERO) < 0) {
            payAmount = BigDecimal.ZERO;
        }
        Cinema cinema = cinemaMapper.selectById(showtime.getCinemaId());
        Hall hall = hallMapper.selectById(showtime.getHallId());
        Movie movie = movieMapper.selectById(showtime.getMovieId());
        if (movie == null) {
            throw new BusinessException("影片不存在");
        }
        TicketOrder order = new TicketOrder();
        order.setOrderNo("TK" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(4));
        order.setUserId(userId);
        order.setShowtimeId(dto.getShowtimeId());
        order.setMovieTitle(movie.getTitle());
        order.setCinemaName(cinema == null ? "" : cinema.getName());
        order.setHallName(hall == null ? "" : hall.getName());
        order.setShowTime(showtime.getStartTime());
        order.setSeatInfo(seatService.buildSeatInfo(seats));
        order.setTotalAmount(totalAmount);
        order.setDiscountAmount(discountAmount);
        order.setPayAmount(payAmount);
        order.setCouponId(userCouponId);
        order.setStatus(STATUS_WAIT_PAY);
        ticketOrderMapper.insert(order);
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", order.getId());
        result.put("orderNo", order.getOrderNo());
        result.put("totalAmount", totalAmount);
        result.put("discountAmount", discountAmount);
        result.put("payAmount", payAmount);
        return result;
    }

    public PageResult<TicketOrder> page(Integer pageNum, Integer pageSize, String orderNo, String status, Long userId, String role) {
        Page<TicketOrder> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TicketOrder> wrapper = new LambdaQueryWrapper<TicketOrder>()
                .like(orderNo != null && !orderNo.trim().isEmpty(), TicketOrder::getOrderNo, orderNo == null ? null : orderNo.trim())
                .eq(status != null && !status.trim().isEmpty(), TicketOrder::getStatus, status == null ? null : status.trim())
                .eq("USER".equals(role), TicketOrder::getUserId, userId)
                .orderByDesc(TicketOrder::getId);
        Page<TicketOrder> resultPage = ticketOrderMapper.selectPage(page, wrapper);
        PageResult<TicketOrder> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public TicketOrder getById(Long id, Long userId, String role) {
        TicketOrder order = ticketOrderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if ("USER".equals(role) && !order.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权限");
        }
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(Long id, Long userId, String role) {
        TicketOrder order = getById(id, userId, role);
        if (!STATUS_WAIT_PAY.equals(order.getStatus())) {
            throw new BusinessException("当前状态不可取消");
        }
        List<Long> seatIds = seatService.parseSeatIds(order.getSeatInfo());
        if (!seatIds.isEmpty()) {
            seatService.unlockForOrder(order.getShowtimeId(), seatIds);
        }
        order.setStatus(STATUS_CANCELED);
        ticketOrderMapper.updateById(order);
    }

    public void finish(Long id, Long userId) {
        TicketOrder order = ticketOrderMapper.selectById(id);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (!STATUS_PAID.equals(order.getStatus())) {
            throw new BusinessException("当前状态不可完成");
        }
        order.setStatus(STATUS_FINISHED);
        ticketOrderMapper.updateById(order);
    }

    @Transactional(rollbackFor = Exception.class)
    public void refundByAdmin(Long id) {
        TicketOrder order = ticketOrderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (!STATUS_PAID.equals(order.getStatus()) && !STATUS_FINISHED.equals(order.getStatus())) {
            throw new BusinessException("当前状态不可退款");
        }
        List<Long> seatIds = seatService.parseSeatIds(order.getSeatInfo());
        if (!seatIds.isEmpty()) {
            seatService.releaseSold(order.getShowtimeId(), seatIds);
        }
        if (order.getCouponId() != null) {
            couponService.rollbackUsed(order.getUserId(), order.getCouponId());
        }
        order.setStatus(STATUS_REFUNDED);
        ticketOrderMapper.updateById(order);
    }

    public TicketOrder getPayableOrder(Long userId, Long orderId) {
        TicketOrder order = ticketOrderMapper.selectById(orderId);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (!STATUS_WAIT_PAY.equals(order.getStatus())) {
            throw new BusinessException("订单状态不可支付");
        }
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    public TicketOrder paySuccess(Long userId, Long orderId) {
        TicketOrder order = getPayableOrder(userId, orderId);
        List<Long> seatIds = seatService.parseSeatIds(order.getSeatInfo());
        if (!seatIds.isEmpty()) {
            seatService.occupy(order.getShowtimeId(), seatIds);
        }
        order.setStatus(STATUS_PAID);
        order.setPayTime(LocalDateTime.now());
        ticketOrderMapper.updateById(order);
        if (order.getCouponId() != null) {
            couponService.markUsed(userId, order.getCouponId());
        }
        for (Long seatId : seatIds) {
            Ticket ticket = new Ticket();
            ticket.setOrderId(order.getId());
            String no = "ET" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(6) + seatId;
            ticket.setTicketNo(no);
            ticket.setQrCode("QRCODE-" + no);
            ticket.setStatus("UNUSED");
            ticketMapper.insert(ticket);
        }
        return order;
    }

    public Long countAll() {
        return ticketOrderMapper.selectCount(null);
    }

    public Long countTodayOrders() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        return ticketOrderMapper.selectCount(new LambdaQueryWrapper<TicketOrder>()
                .ge(TicketOrder::getCreateTime, start)
                .lt(TicketOrder::getCreateTime, end));
    }

    public List<TicketOrder> listPaidOrders() {
        return ticketOrderMapper.selectList(new LambdaQueryWrapper<TicketOrder>()
                .in(TicketOrder::getStatus, STATUS_PAID, STATUS_FINISHED)
                .orderByDesc(TicketOrder::getPayTime));
    }
}
