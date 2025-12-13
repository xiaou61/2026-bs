package com.xiaou.ticket.service;

import cn.hutool.core.util.IdUtil;
import com.xiaou.ticket.entity.*;
import com.xiaou.ticket.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    public TicketOrder createOrder(Long userId, Long matchId, List<Long> seatIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (Long seatId : seatIds) {
            Seat seat = seatRepository.findSeatById(seatId)
                    .orElseThrow(() -> new RuntimeException("Seat not found"));
            
            if (!"AVAILABLE".equals(seat.getStatus())) {
                throw new RuntimeException("Seat already occupied");
            }
        }
        
        TicketOrder order = new TicketOrder();
        order.setOrderNo("ORD" + IdUtil.getSnowflakeNextIdStr());
        order.setUserId(userId);
        order.setMatchId(matchId);
        order.setTotalAmount(totalAmount);
        order.setPaymentAmount(totalAmount);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setStatus("PENDING");
        
        order = orderRepository.save(order);
        
        for (Long seatId : seatIds) {
            seatRepository.updateSeatStatus(seatId, "LOCKED");
        }
        
        return order;
    }
    
    @Transactional
    public TicketOrder payOrder(Long orderId, String paymentMethod) {
        TicketOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if (!"PENDING".equals(order.getStatus())) {
            throw new RuntimeException("Order cannot be paid");
        }
        
        User user = userRepository.findById(order.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (user.getBalance().compareTo(order.getPaymentAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        user.setBalance(user.getBalance().subtract(order.getPaymentAmount()));
        userRepository.save(user);
        
        order.setStatus("PAID");
        order.setPaymentMethod(paymentMethod);
        order.setPaymentTime(LocalDateTime.now());
        orderRepository.save(order);
        
        return order;
    }
    
    public List<TicketOrder> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    
    public TicketOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
    
    @Transactional
    public void cancelOrder(Long orderId) {
        TicketOrder order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        if ("PAID".equals(order.getStatus())) {
            User user = userRepository.findById(order.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setBalance(user.getBalance().add(order.getPaymentAmount()));
            userRepository.save(user);
        }
        
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }
}
