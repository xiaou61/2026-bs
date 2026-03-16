package com.railway.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.railway.common.BusinessException;
import com.railway.common.PageResult;
import com.railway.dto.OrderCreateDTO;
import com.railway.entity.PassengerProfile;
import com.railway.entity.Schedule;
import com.railway.entity.Seat;
import com.railway.entity.Station;
import com.railway.entity.Ticket;
import com.railway.entity.TicketOrder;
import com.railway.entity.Train;
import com.railway.mapper.TicketMapper;
import com.railway.mapper.TicketOrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    public static final String STATUS_CANCELED = "CANCELED";
    public static final String STATUS_REFUNDED = "REFUNDED";
    public static final String STATUS_CHANGED = "CHANGED";
    public static final String STATUS_FINISHED = "FINISHED";

    @Resource
    private TicketOrderMapper ticketOrderMapper;

    @Resource
    private TicketMapper ticketMapper;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private SeatService seatService;

    @Resource
    private PassengerService passengerService;

    @Resource
    private TrainService trainService;

    @Resource
    private StationService stationService;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(Long userId, OrderCreateDTO dto) {
        if (dto == null || dto.getScheduleId() == null || dto.getSeatIds() == null || dto.getSeatIds().isEmpty()) {
            throw new BusinessException("订单参数不完整");
        }
        if (dto.getPassengerIds() == null || dto.getPassengerIds().isEmpty()) {
            throw new BusinessException("乘车人不能为空");
        }
        Schedule schedule = scheduleService.getById(dto.getScheduleId());
        if (!"ON_SALE".equals(schedule.getSaleStatus()) || schedule.getStatus() == null || schedule.getStatus() != 1) {
            throw new BusinessException("当前班次不可售");
        }
        List<Seat> seats = seatService.getSeats(dto.getScheduleId(), dto.getSeatIds());
        List<PassengerProfile> passengers = passengerService.getByIds(userId, dto.getPassengerIds());
        if (seats.size() != passengers.size()) {
            throw new BusinessException("座位数量与乘车人数必须一致");
        }
        for (Seat seat : seats) {
            if (!seatService.isLockedByUser(userId, dto.getScheduleId(), seat.getId())) {
                throw new BusinessException("请先锁定座位后再下单");
            }
        }
        Train train = trainService.getById(schedule.getTrainId());
        Station departureStation = stationService.getById(schedule.getDepartureStationId());
        Station arrivalStation = stationService.getById(schedule.getArrivalStationId());
        BigDecimal totalAmount = seats.stream()
                .map(seat -> seat.getPrice() == null ? schedule.getBasePrice() : seat.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        TicketOrder order = new TicketOrder();
        order.setOrderNo("RT" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(4));
        order.setUserId(userId);
        order.setScheduleId(dto.getScheduleId());
        order.setTrainCode(train.getTrainCode());
        order.setDepartureStation(departureStation.getStationName());
        order.setArrivalStation(arrivalStation.getStationName());
        order.setDepartureTime(schedule.getDepartureTime());
        order.setArrivalTime(schedule.getArrivalTime());
        order.setSeatInfo(seatService.buildSeatInfo(seats));
        order.setPassengerNames(passengers.stream().map(PassengerProfile::getPassengerName).collect(Collectors.joining("、")));
        order.setPassengerInfo(passengers.stream().map(item -> item.getPassengerName() + "|" + item.getIdCard()).collect(Collectors.joining(",")));
        order.setContactPhone(StringUtils.hasText(dto.getContactPhone()) ? dto.getContactPhone().trim() : null);
        order.setTotalAmount(totalAmount);
        order.setPayAmount(totalAmount);
        order.setStatus(STATUS_WAIT_PAY);
        ticketOrderMapper.insert(order);
        Map<String, Object> result = new HashMap<>();
        result.put("orderId", order.getId());
        result.put("orderNo", order.getOrderNo());
        result.put("totalAmount", totalAmount);
        result.put("payAmount", totalAmount);
        return result;
    }

    public PageResult<TicketOrder> page(Integer pageNum, Integer pageSize, String orderNo, String status, Long userId, String role) {
        Page<TicketOrder> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TicketOrder> wrapper = new LambdaQueryWrapper<TicketOrder>()
                .like(StringUtils.hasText(orderNo), TicketOrder::getOrderNo, orderNo == null ? null : orderNo.trim())
                .eq(StringUtils.hasText(status), TicketOrder::getStatus, status == null ? null : status.trim())
                .eq("USER".equalsIgnoreCase(role), TicketOrder::getUserId, userId)
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
        if ("USER".equalsIgnoreCase(role) && !order.getUserId().equals(userId)) {
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
            seatService.unlockForOrder(order.getScheduleId(), seatIds);
        }
        order.setStatus(STATUS_CANCELED);
        ticketOrderMapper.updateById(order);
    }

    public void finish(Long id, Long userId) {
        TicketOrder order = getById(id, userId, "USER");
        if (!STATUS_PAID.equals(order.getStatus())) {
            throw new BusinessException("当前状态不可完成");
        }
        order.setStatus(STATUS_FINISHED);
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
        List<String> seatLabels = seatService.parseSeatLabels(order.getSeatInfo());
        List<String> passengerPairs = parsePassengerInfo(order.getPassengerInfo());
        seatService.occupy(order.getScheduleId(), seatIds);
        order.setStatus(STATUS_PAID);
        order.setPayTime(LocalDateTime.now());
        ticketOrderMapper.updateById(order);
        for (int i = 0; i < seatLabels.size(); i++) {
            String[] pair = passengerPairs.size() > i ? passengerPairs.get(i).split("\\|") : new String[]{"乘客" + (i + 1), ""};
            Ticket ticket = new Ticket();
            ticket.setOrderId(order.getId());
            String no = "TK" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(6) + (i + 1);
            ticket.setTicketNo(no);
            ticket.setPassengerName(pair[0]);
            ticket.setIdCard(pair.length > 1 ? pair[1] : "");
            ticket.setSeatLabel(seatLabels.get(i));
            ticket.setQrCode("QRCODE-" + no);
            ticket.setStatus("UNUSED");
            ticketMapper.insert(ticket);
        }
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    public TicketOrder refundBySystem(Long orderId) {
        TicketOrder order = ticketOrderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (!STATUS_PAID.equals(order.getStatus()) && !STATUS_FINISHED.equals(order.getStatus())) {
            throw new BusinessException("当前状态不可退票");
        }
        List<Long> seatIds = seatService.parseSeatIds(order.getSeatInfo());
        if (!seatIds.isEmpty()) {
            seatService.releaseSold(order.getScheduleId(), seatIds);
        }
        List<Ticket> tickets = ticketMapper.selectList(new LambdaQueryWrapper<Ticket>().eq(Ticket::getOrderId, order.getId()));
        for (Ticket ticket : tickets) {
            ticket.setStatus("REFUNDED");
            ticketMapper.updateById(ticket);
        }
        order.setStatus(STATUS_REFUNDED);
        ticketOrderMapper.updateById(order);
        return order;
    }

    @Transactional(rollbackFor = Exception.class)
    public void markChanged(Long orderId) {
        TicketOrder order = ticketOrderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (!STATUS_PAID.equals(order.getStatus()) && !STATUS_FINISHED.equals(order.getStatus())) {
            throw new BusinessException("当前状态不可改签");
        }
        List<Ticket> tickets = ticketMapper.selectList(new LambdaQueryWrapper<Ticket>().eq(Ticket::getOrderId, orderId));
        for (Ticket ticket : tickets) {
            ticket.setStatus("CHANGED");
            ticketMapper.updateById(ticket);
        }
        order.setStatus(STATUS_CHANGED);
        ticketOrderMapper.updateById(order);
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
                .in(TicketOrder::getStatus, STATUS_PAID, STATUS_FINISHED, STATUS_CHANGED)
                .orderByDesc(TicketOrder::getPayTime));
    }

    private List<String> parsePassengerInfo(String passengerInfo) {
        return passengerInfo == null || passengerInfo.trim().isEmpty()
                ? java.util.Collections.emptyList()
                : java.util.Arrays.stream(passengerInfo.split(",")).collect(Collectors.toList());
    }
}
