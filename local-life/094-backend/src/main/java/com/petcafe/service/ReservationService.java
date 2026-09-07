package com.petcafe.service;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcafe.common.BusinessException;
import com.petcafe.common.PageResult;
import com.petcafe.dto.ReservationDTO;
import com.petcafe.entity.CafeShop;
import com.petcafe.entity.ReservationRecord;
import com.petcafe.entity.SeatInfo;
import com.petcafe.entity.User;
import com.petcafe.mapper.CafeShopMapper;
import com.petcafe.mapper.ReservationRecordMapper;
import com.petcafe.mapper.SeatInfoMapper;
import com.petcafe.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_CANCELED = "CANCELED";

    @Resource
    private ReservationRecordMapper reservationMapper;

    @Resource
    private SeatService seatService;

    @Resource
    private SeatInfoMapper seatMapper;

    @Resource
    private CafeShopMapper shopMapper;

    @Resource
    private UserMapper userMapper;

    public PageResult<ReservationRecord> page(Integer pageNum, Integer pageSize, Long shopId, String status, Long userId, String role) {
        Page<ReservationRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ReservationRecord> wrapper = new LambdaQueryWrapper<ReservationRecord>()
                .eq(shopId != null, ReservationRecord::getShopId, shopId)
                .eq(StringUtils.hasText(status), ReservationRecord::getStatus, status == null ? null : status.trim().toUpperCase())
                .eq("CUSTOMER".equalsIgnoreCase(role), ReservationRecord::getUserId, userId)
                .orderByDesc(ReservationRecord::getId);
        Page<ReservationRecord> resultPage = reservationMapper.selectPage(page, wrapper);
        fillDetail(resultPage.getRecords());
        PageResult<ReservationRecord> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> create(Long userId, ReservationDTO dto) {
        if (dto == null || dto.getShopId() == null || dto.getSeatId() == null || dto.getVisitDate() == null || !StringUtils.hasText(dto.getVisitTime()) || dto.getPeopleCount() == null || dto.getPeopleCount() <= 0) {
            throw new BusinessException("预约信息不完整");
        }
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        CafeShop shop = shopMapper.selectById(dto.getShopId());
        if (shop == null || !"OPEN".equalsIgnoreCase(shop.getStatus())) {
            throw new BusinessException("门店不可预约");
        }
        SeatInfo seat = seatService.requireById(dto.getSeatId());
        if (!dto.getShopId().equals(seat.getShopId())) {
            throw new BusinessException("门店与座位不匹配");
        }
        if (dto.getPeopleCount() > seat.getCapacity()) {
            throw new BusinessException("预约人数超出座位容量");
        }
        if (dto.getVisitDate().isBefore(LocalDate.now())) {
            throw new BusinessException("预约日期不能早于今天");
        }
        seatService.occupySeat(seat.getId());
        ReservationRecord record = new ReservationRecord();
        record.setReservationNo("RSV" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + RandomUtil.randomNumbers(4));
        record.setUserId(userId);
        record.setShopId(dto.getShopId());
        record.setSeatId(dto.getSeatId());
        record.setCustomerName(StringUtils.hasText(dto.getCustomerName()) ? dto.getCustomerName().trim() : (StringUtils.hasText(user.getNickname()) ? user.getNickname() : user.getUsername()));
        record.setCustomerPhone(StringUtils.hasText(dto.getCustomerPhone()) ? dto.getCustomerPhone().trim() : user.getPhone());
        record.setVisitDate(dto.getVisitDate());
        record.setVisitTime(dto.getVisitTime().trim());
        record.setPeopleCount(dto.getPeopleCount());
        record.setPetCompanion(StringUtils.hasText(dto.getPetCompanion()) ? dto.getPetCompanion().trim() : "无");
        record.setRemark(dto.getRemark());
        record.setStatus(STATUS_CONFIRMED);
        reservationMapper.insert(record);
        Map<String, Object> result = new HashMap<>();
        result.put("reservationId", record.getId());
        result.put("reservationNo", record.getReservationNo());
        return result;
    }

    public ReservationRecord getById(Long id, Long userId, String role) {
        ReservationRecord record = reservationMapper.selectById(id);
        if (record == null) {
            throw new BusinessException("预约不存在");
        }
        if ("CUSTOMER".equalsIgnoreCase(role) && !record.getUserId().equals(userId)) {
            throw new BusinessException(403, "无权限");
        }
        fillDetail(java.util.Collections.singletonList(record));
        return record;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id, String status, String managerRemark) {
        ReservationRecord record = reservationMapper.selectById(id);
        if (record == null) {
            throw new BusinessException("预约不存在");
        }
        String finalStatus = StringUtils.hasText(status) ? status.trim().toUpperCase() : STATUS_COMPLETED;
        if (!STATUS_CONFIRMED.equals(finalStatus) && !STATUS_COMPLETED.equals(finalStatus) && !STATUS_CANCELED.equals(finalStatus)) {
            throw new BusinessException("预约状态不合法");
        }
        record.setStatus(finalStatus);
        record.setManagerRemark(StringUtils.hasText(managerRemark) ? managerRemark.trim() : null);
        reservationMapper.updateById(record);
        if (STATUS_COMPLETED.equals(finalStatus) || STATUS_CANCELED.equals(finalStatus)) {
            seatService.releaseSeat(record.getSeatId());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(Long id, Long userId, String role) {
        ReservationRecord record = getById(id, userId, role);
        if (STATUS_COMPLETED.equals(record.getStatus()) || STATUS_CANCELED.equals(record.getStatus())) {
            throw new BusinessException("当前状态不可取消");
        }
        record.setStatus(STATUS_CANCELED);
        reservationMapper.updateById(record);
        seatService.releaseSeat(record.getSeatId());
    }

    public Long countTodayReservations() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        return reservationMapper.selectCount(new LambdaQueryWrapper<ReservationRecord>()
                .ge(ReservationRecord::getCreateTime, start)
                .lt(ReservationRecord::getCreateTime, end));
    }

    private void fillDetail(List<ReservationRecord> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Map<Long, User> userMap = userMapper.selectList(new LambdaQueryWrapper<User>()
                        .in(User::getId, list.stream().map(ReservationRecord::getUserId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(User::getId, item -> item, (a, b) -> a, HashMap::new));
        Map<Long, String> shopMap = shopMapper.selectList(new LambdaQueryWrapper<CafeShop>()
                        .in(CafeShop::getId, list.stream().map(ReservationRecord::getShopId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(CafeShop::getId, CafeShop::getName, (a, b) -> a, HashMap::new));
        Map<Long, String> seatMap = seatMapper.selectList(new LambdaQueryWrapper<SeatInfo>()
                        .in(SeatInfo::getId, list.stream().map(ReservationRecord::getSeatId).distinct().collect(Collectors.toList())))
                .stream()
                .collect(Collectors.toMap(SeatInfo::getId, SeatInfo::getSeatNo, (a, b) -> a, HashMap::new));
        list.forEach(item -> {
            User user = userMap.get(item.getUserId());
            item.setUsername(user == null ? null : user.getUsername());
            item.setNickname(user == null ? null : user.getNickname());
            item.setShopName(shopMap.get(item.getShopId()));
            item.setSeatNo(seatMap.get(item.getSeatId()));
        });
    }
}
