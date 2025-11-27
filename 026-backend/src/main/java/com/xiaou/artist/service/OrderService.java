package com.xiaou.artist.service;

import com.xiaou.artist.entity.Order;
import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getOrdersByUserId(Long userId);
    List<Order> getOrdersByArtistId(Long artistId);
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    boolean payDeposit(Long id);
    boolean submitDraft(Long id, String url);
    boolean confirmDraft(Long id);
    boolean requestRevise(Long id);
    boolean submitFinal(Long id, String url);
    boolean payFinalPayment(Long id);
    boolean completeOrder(Long id);
}
