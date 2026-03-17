package com.vending.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vending.common.BusinessException;
import com.vending.common.PageResult;
import com.vending.dto.OrderCreateDTO;
import com.vending.entity.MachineSlot;
import com.vending.entity.ProductInfo;
import com.vending.entity.SaleOrder;
import com.vending.entity.User;
import com.vending.entity.VendingMachine;
import com.vending.mapper.ProductInfoMapper;
import com.vending.mapper.SaleOrderMapper;
import com.vending.mapper.UserMapper;
import com.vending.mapper.VendingMachineMapper;
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
import java.util.stream.Collectors;

@Service
public class OrderService {

    public static final String STATUS_WAIT_PAY = "WAIT_PAY";
    public static final String STATUS_PAID = "PAID";
    public static final String STATUS_SHIPPED = "SHIPPED";
    public static final String STATUS_FAILED = "FAILED";
    public static final String STATUS_REFUNDED = "REFUNDED";
    public static final String STATUS_CANCELED = "CANCELED";

    @Resource
    private SaleOrderMapper orderMapper;

    @Resource
    private SlotService slotService;

    @Resource
    private ProductInfoMapper productMapper;

    @Resource
    private VendingMachineMapper machineMapper;

    @Resource
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(Long userId, OrderCreateDTO dto) {
        if (dto == null || dto.getMachineId() == null || dto.getSlotId() == null || dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new BusinessException("订单参数不完整");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        MachineSlot slot = slotService.requireById(dto.getSlotId());
        if (!dto.getMachineId().equals(slot.getMachineId())) {
            throw new BusinessException("设备与货道不匹配");
        }
        if ("DISABLED".equalsIgnoreCase(slot.getStatus())) {
            throw new BusinessException("该货道不可购买");
        }
        int stock = slot.getCurrentStock() == null ? 0 : slot.getCurrentStock();
        if (stock < dto.getQuantity()) {
            throw new BusinessException("库存不足");
        }
        ProductInfo product = productMapper.selectById(slot.getProductId());
        if (product == null || product.getStatus() == null || product.getStatus() == 0) {
            throw new BusinessException("商品不可购买");
        }
        VendingMachine machine = machineMapper.selectById(dto.getMachineId());
        if (machine == null) {
            throw new BusinessException("设备不存在");
        }
        BigDecimal unitPrice = product.getSalePrice() == null ? BigDecimal.ZERO : product.getSalePrice();
        BigDecimal totalAmount = unitPrice.multiply(BigDecimal.valueOf(dto.getQuantity()));
        SaleOrder order = new SaleOrder();
        order.setOrderNo("SO" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(4));
        order.setUserId(userId);
        order.setMachineId(machine.getId());
        order.setSlotId(slot.getId());
        order.setProductId(product.getId());
        order.setQuantity(dto.getQuantity());
        order.setTotalAmount(totalAmount);
        order.setPayAmount(totalAmount);
        order.setPickupCode("PK" + RandomUtil.randomNumbers(6));
        order.setStatus(STATUS_WAIT_PAY);
        orderMapper.insert(order);
        Map<String, Object> map = new HashMap<>();
        map.put("orderId", order.getId());
        map.put("orderNo", order.getOrderNo());
        map.put("payAmount", order.getPayAmount());
        map.put("pickupCode", order.getPickupCode());
        return map;
    }

    public PageResult<SaleOrder> page(Integer pageNum, Integer pageSize, String orderNo, String status, Long userId, String role) {
        Page<SaleOrder> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SaleOrder> wrapper = new LambdaQueryWrapper<SaleOrder>()
                .like(orderNo != null && !orderNo.trim().isEmpty(), SaleOrder::getOrderNo, orderNo == null ? null : orderNo.trim())
                .eq(status != null && !status.trim().isEmpty(), SaleOrder::getStatus, status == null ? null : status.trim().toUpperCase())
                .eq("CUSTOMER".equalsIgnoreCase(role), SaleOrder::getUserId, userId)
                .orderByDesc(SaleOrder::getId);
        Page<SaleOrder> resultPage = orderMapper.selectPage(page, wrapper);
        fillDetail(resultPage.getRecords());
        PageResult<SaleOrder> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public SaleOrder getById(Long id, Long userId, String role) {
        SaleOrder order = orderMapper.selectById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if ("CUSTOMER".equalsIgnoreCase(role) && !order.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权限");
        }
        fillDetail(java.util.Collections.singletonList(order));
        return order;
    }

    public SaleOrder getPayableOrder(Long userId, Long orderId) {
        SaleOrder order = orderMapper.selectById(orderId);
        if (order == null || !order.getUserId().equals(userId)) {
            throw new BusinessException("订单不存在");
        }
        if (!STATUS_WAIT_PAY.equals(order.getStatus())) {
            throw new BusinessException("订单状态不可支付");
        }
        return order;
    }

    public void cancel(Long id, Long userId, String role) {
        SaleOrder order = getById(id, userId, role);
        if (!STATUS_WAIT_PAY.equals(order.getStatus())) {
            throw new BusinessException("当前状态不可取消");
        }
        order.setStatus(STATUS_CANCELED);
        orderMapper.updateById(order);
    }

    public void markPaid(Long orderId) {
        SaleOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        order.setStatus(STATUS_PAID);
        order.setPayTime(LocalDateTime.now());
        orderMapper.updateById(order);
    }

    public void markShipped(Long orderId) {
        SaleOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        order.setStatus(STATUS_SHIPPED);
        orderMapper.updateById(order);
    }

    public Long countAll() {
        return orderMapper.selectCount(null);
    }

    public Long countTodayOrders() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        return orderMapper.selectCount(new LambdaQueryWrapper<SaleOrder>()
                .ge(SaleOrder::getCreateTime, start)
                .lt(SaleOrder::getCreateTime, end));
    }

    public List<SaleOrder> listFinishedOrders() {
        List<SaleOrder> list = orderMapper.selectList(new LambdaQueryWrapper<SaleOrder>()
                .in(SaleOrder::getStatus, STATUS_PAID, STATUS_SHIPPED)
                .orderByDesc(SaleOrder::getPayTime));
        fillDetail(list);
        return list;
    }

    private void fillDetail(List<SaleOrder> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, User> userMap = userMapper.selectList(new LambdaQueryWrapper<User>()
                        .in(User::getId, list.stream().map(SaleOrder::getUserId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(User::getId, item -> item, (a, b) -> a, HashMap::new));
        Map<Long, String> machineMap = machineMapper.selectList(new LambdaQueryWrapper<VendingMachine>()
                        .in(VendingMachine::getId, list.stream().map(SaleOrder::getMachineId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(VendingMachine::getId, VendingMachine::getName, (a, b) -> a, HashMap::new));
        Map<Long, MachineSlot> slotMap = list.stream()
                .map(item -> slotService.requireById(item.getSlotId()))
                .collect(Collectors.toMap(MachineSlot::getId, item -> item, (a, b) -> a, HashMap::new));
        Map<Long, String> productMap = productMapper.selectList(new LambdaQueryWrapper<ProductInfo>()
                        .in(ProductInfo::getId, list.stream().map(SaleOrder::getProductId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(ProductInfo::getId, ProductInfo::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            User user = userMap.get(item.getUserId());
            MachineSlot slot = slotMap.get(item.getSlotId());
            item.setUsername(user == null ? null : user.getUsername());
            item.setNickname(user == null ? null : user.getNickname());
            item.setMachineName(machineMap.get(item.getMachineId()));
            item.setSlotNo(slot == null ? null : slot.getSlotNo());
            item.setProductName(productMap.get(item.getProductId()));
        });
    }
}
