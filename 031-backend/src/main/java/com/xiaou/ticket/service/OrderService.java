package com.xiaou.ticket.service;

import cn.hutool.core.util.IdUtil;
import com.xiaou.ticket.dto.OrderCreateRequest;
import com.xiaou.ticket.entity.MatchPricing;
import com.xiaou.ticket.entity.Seat;
import com.xiaou.ticket.entity.Ticket;
import com.xiaou.ticket.entity.TicketOrder;
import com.xiaou.ticket.entity.User;
import com.xiaou.ticket.repository.OrderRepository;
import com.xiaou.ticket.repository.SeatRepository;
import com.xiaou.ticket.repository.TicketRepository;
import com.xiaou.ticket.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    public OrderService(OrderRepository orderRepository,
                        TicketRepository ticketRepository,
                        UserRepository userRepository,
                        SeatRepository seatRepository) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public TicketOrder createOrder(Long userId, OrderCreateRequest request) {
        if (request == null || request.getMatchId() == null || request.getPricingId() == null
                || request.getSeatIds() == null || request.getSeatIds().isEmpty()) {
            throw new RuntimeException("下单参数不完整");
        }

        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        List<Long> seatIds = request.getSeatIds().stream().distinct().toList();
        MatchPricing pricing = seatRepository.findPricingById(request.getPricingId())
                .orElseThrow(() -> new RuntimeException("票价不存在"));
        if (!pricing.getMatchId().equals(request.getMatchId())) {
            throw new RuntimeException("票价分区与比赛不匹配");
        }

        List<Seat> seats = seatRepository.findByIds(seatIds);
        if (seats.size() != seatIds.size()) {
            throw new RuntimeException("部分座位不存在");
        }
        if (seats.stream().anyMatch(seat -> !pricing.getCategoryId().equals(seat.getCategoryId()))) {
            throw new RuntimeException("所选座位不属于当前票价分区");
        }
        if (seats.stream().anyMatch(seat -> !"AVAILABLE".equalsIgnoreCase(seat.getStatus()))) {
            throw new RuntimeException("存在不可售座位");
        }

        List<Long> occupiedSeatIds = ticketRepository.findActiveSeatIds(request.getMatchId(), seatIds);
        if (!occupiedSeatIds.isEmpty()) {
            throw new RuntimeException("部分座位已被占用，请重新选择");
        }
        if (pricing.getAvailableSeats() < seatIds.size()) {
            throw new RuntimeException("可售座位不足");
        }

        BigDecimal totalAmount = pricing.getPrice().multiply(BigDecimal.valueOf(seatIds.size()));
        TicketOrder order = new TicketOrder();
        order.setOrderNo("ORD" + IdUtil.getSnowflakeNextIdStr());
        order.setUserId(userId);
        order.setMatchId(request.getMatchId());
        order.setTotalAmount(totalAmount);
        order.setPaymentAmount(totalAmount);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setStatus("PENDING");
        order = orderRepository.save(order);

        for (Seat seat : seats) {
            Ticket ticket = new Ticket();
            ticket.setTicketNo("TKT" + IdUtil.getSnowflakeNextIdStr());
            ticket.setOrderId(order.getId());
            ticket.setMatchId(request.getMatchId());
            ticket.setSeatId(seat.getId());
            ticket.setCategoryId(seat.getCategoryId());
            ticket.setPrice(pricing.getPrice());
            ticket.setStatus("LOCKED");
            ticketRepository.save(ticket);
        }
        seatRepository.updateAvailableSeats(pricing.getId(), -seatIds.size());
        return orderRepository.findById(order.getId()).orElse(order);
    }

    @Transactional
    public TicketOrder payOrder(Long orderId, String paymentMethod) {
        TicketOrder order = getOrderById(orderId);
        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("当前订单不可支付");
        }

        User user = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        if (user.getBalance().compareTo(order.getPaymentAmount()) < 0) {
            throw new RuntimeException("余额不足，请先充值");
        }

        List<Ticket> tickets = ticketRepository.findByOrderId(orderId);
        if (tickets.isEmpty()) {
            throw new RuntimeException("订单中没有可支付门票");
        }

        user.setBalance(user.getBalance().subtract(order.getPaymentAmount()));
        userRepository.save(user);

        String finalPaymentMethod = paymentMethod == null || paymentMethod.isBlank() ? "BALANCE" : paymentMethod;
        order.setStatus("PAID");
        order.setPaymentMethod(finalPaymentMethod);
        order.setPaymentTime(LocalDateTime.now());
        order = orderRepository.save(order);

        for (Ticket ticket : tickets) {
            ticketRepository.updateTicketStatus(ticket.getId(), "VALID", buildQrCode(order, ticket));
        }
        return orderRepository.findById(orderId).orElse(order);
    }

    public List<TicketOrder> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public TicketOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
    }

    public List<Ticket> getOrderTickets(Long orderId) {
        getOrderById(orderId);
        return ticketRepository.findByOrderId(orderId);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        TicketOrder order = getOrderById(orderId);
        if ("CANCELLED".equals(order.getStatus())) {
            throw new RuntimeException("订单已取消");
        }

        List<Ticket> tickets = ticketRepository.findByOrderId(orderId);
        if ("PAID".equals(order.getStatus())) {
            User user = userRepository.findById(order.getUserId())
                    .orElseThrow(() -> new RuntimeException("用户不存在"));
            user.setBalance(user.getBalance().add(order.getPaymentAmount()));
            userRepository.save(user);
        }

        Map<Long, Long> grouped = tickets.stream()
                .collect(Collectors.groupingBy(Ticket::getCategoryId, Collectors.counting()));
        for (Map.Entry<Long, Long> entry : grouped.entrySet()) {
            MatchPricing pricing = seatRepository.findPricingByMatchIdAndCategoryId(order.getMatchId(), entry.getKey())
                    .orElseThrow(() -> new RuntimeException("票价分区不存在"));
            seatRepository.updateAvailableSeats(pricing.getId(), entry.getValue().intValue());
        }
        for (Ticket ticket : tickets) {
            ticketRepository.updateTicketStatus(ticket.getId(), "CANCELLED", null);
        }

        order.setStatus("CANCELLED");
        order.setPaymentMethod(order.getPaymentMethod());
        orderRepository.save(order);
    }

    private String buildQrCode(TicketOrder order, Ticket ticket) {
        return "QR-" + order.getOrderNo() + "-" + ticket.getTicketNo();
    }
}
