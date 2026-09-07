package com.petcafe.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.petcafe.common.BusinessException;
import com.petcafe.common.PageResult;
import com.petcafe.entity.CafeShop;
import com.petcafe.entity.SeatInfo;
import com.petcafe.mapper.CafeShopMapper;
import com.petcafe.mapper.SeatInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SeatService {

    @Resource
    private SeatInfoMapper seatMapper;

    @Resource
    private CafeShopMapper shopMapper;

    public PageResult<SeatInfo> page(Integer pageNum, Integer pageSize, Long shopId, String reservationStatus) {
        Page<SeatInfo> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<SeatInfo> wrapper = new LambdaQueryWrapper<SeatInfo>()
                .eq(shopId != null, SeatInfo::getShopId, shopId)
                .eq(StringUtils.hasText(reservationStatus), SeatInfo::getReservationStatus, reservationStatus == null ? null : reservationStatus.trim().toUpperCase())
                .orderByDesc(SeatInfo::getId);
        Page<SeatInfo> resultPage = seatMapper.selectPage(page, wrapper);
        fillShopName(resultPage.getRecords());
        PageResult<SeatInfo> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<SeatInfo> listByShop(Long shopId) {
        List<SeatInfo> list = seatMapper.selectList(new LambdaQueryWrapper<SeatInfo>()
                .eq(shopId != null, SeatInfo::getShopId, shopId)
                .orderByAsc(SeatInfo::getSeatNo));
        fillShopName(list);
        return list;
    }

    public SeatInfo getById(Long id) {
        SeatInfo seat = seatMapper.selectById(id);
        if (seat == null) {
            throw new BusinessException("座位不存在");
        }
        fillShopName(java.util.Collections.singletonList(seat));
        return seat;
    }

    public SeatInfo requireById(Long id) {
        SeatInfo seat = seatMapper.selectById(id);
        if (seat == null) {
            throw new BusinessException("座位不存在");
        }
        return seat;
    }

    public void save(SeatInfo seat) {
        if (seat == null || seat.getShopId() == null || !StringUtils.hasText(seat.getSeatNo())) {
            throw new BusinessException("座位信息不完整");
        }
        CafeShop shop = shopMapper.selectById(seat.getShopId());
        if (shop == null) {
            throw new BusinessException("门店不存在");
        }
        validateSeatNo(seat);
        if (seat.getCapacity() == null || seat.getCapacity() <= 0) {
            throw new BusinessException("座位容量必须大于0");
        }
        if (seat.getId() == null) {
            seat.setSeatStatus(StringUtils.hasText(seat.getSeatStatus()) ? seat.getSeatStatus().trim().toUpperCase() : "NORMAL");
            seat.setReservationStatus(StringUtils.hasText(seat.getReservationStatus()) ? seat.getReservationStatus().trim().toUpperCase() : "AVAILABLE");
            seat.setMinConsume(seat.getMinConsume() == null ? BigDecimal.ZERO : seat.getMinConsume());
            seatMapper.insert(seat);
            return;
        }
        SeatInfo db = requireById(seat.getId());
        db.setShopId(seat.getShopId());
        db.setSeatNo(seat.getSeatNo().trim());
        db.setZoneName(StringUtils.hasText(seat.getZoneName()) ? seat.getZoneName().trim() : null);
        db.setCapacity(seat.getCapacity());
        db.setMinConsume(seat.getMinConsume() == null ? db.getMinConsume() : seat.getMinConsume());
        db.setSeatStatus(StringUtils.hasText(seat.getSeatStatus()) ? seat.getSeatStatus().trim().toUpperCase() : db.getSeatStatus());
        db.setReservationStatus(StringUtils.hasText(seat.getReservationStatus()) ? seat.getReservationStatus().trim().toUpperCase() : db.getReservationStatus());
        db.setRemark(StringUtils.hasText(seat.getRemark()) ? seat.getRemark().trim() : null);
        seatMapper.updateById(db);
    }

    public void deleteById(Long id) {
        seatMapper.deleteById(id);
    }

    public void occupySeat(Long seatId) {
        SeatInfo seat = requireById(seatId);
        if (!"AVAILABLE".equalsIgnoreCase(seat.getReservationStatus())) {
            throw new BusinessException("该座位已被预约");
        }
        if ("DISABLED".equalsIgnoreCase(seat.getSeatStatus())) {
            throw new BusinessException("该座位不可预约");
        }
        seat.setReservationStatus("BOOKED");
        seatMapper.updateById(seat);
    }

    public void releaseSeat(Long seatId) {
        SeatInfo seat = requireById(seatId);
        seat.setReservationStatus("AVAILABLE");
        seatMapper.updateById(seat);
    }

    private void validateSeatNo(SeatInfo seat) {
        SeatInfo exist = seatMapper.selectOne(new LambdaQueryWrapper<SeatInfo>()
                .eq(SeatInfo::getShopId, seat.getShopId())
                .eq(SeatInfo::getSeatNo, seat.getSeatNo().trim())
                .last("limit 1"));
        if (exist != null && (seat.getId() == null || !exist.getId().equals(seat.getId()))) {
            throw new BusinessException("同一门店下座位编号已存在");
        }
    }

    private void fillShopName(List<SeatInfo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Long> shopIds = list.stream().map(SeatInfo::getShopId).distinct().collect(Collectors.toList());
        Map<Long, String> shopMap = shopMapper.selectList(new LambdaQueryWrapper<CafeShop>().in(CafeShop::getId, shopIds))
                .stream()
                .collect(Collectors.toMap(CafeShop::getId, CafeShop::getName, (a, b) -> a, HashMap::new));
        list.forEach(item -> item.setShopName(shopMap.get(item.getShopId())));
    }
}
