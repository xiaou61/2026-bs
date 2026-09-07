package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.common.PageResult;
import com.ticket.entity.Coupon;
import com.ticket.entity.UserCoupon;
import com.ticket.mapper.CouponMapper;
import com.ticket.mapper.UserCouponMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CouponService {

    @Resource
    private CouponMapper couponMapper;

    @Resource
    private UserCouponMapper userCouponMapper;

    public PageResult<Coupon> page(Integer pageNum, Integer pageSize, String name, Integer status) {
        Page<Coupon> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Coupon> wrapper = new LambdaQueryWrapper<Coupon>()
                .like(StringUtils.hasText(name), Coupon::getName, name == null ? null : name.trim())
                .eq(status != null, Coupon::getStatus, status)
                .orderByDesc(Coupon::getId);
        Page<Coupon> resultPage = couponMapper.selectPage(page, wrapper);
        PageResult<Coupon> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public List<Coupon> publicList() {
        LocalDateTime now = LocalDateTime.now();
        return couponMapper.selectList(new LambdaQueryWrapper<Coupon>()
                .eq(Coupon::getStatus, 1)
                .le(Coupon::getStartTime, now)
                .ge(Coupon::getEndTime, now)
                .orderByDesc(Coupon::getId));
    }

    public void save(Coupon coupon) {
        if (coupon == null || !StringUtils.hasText(coupon.getName())) {
            throw new BusinessException("优惠券名称不能为空");
        }
        coupon.setName(coupon.getName().trim());
        coupon.setType(StringUtils.hasText(coupon.getType()) ? coupon.getType().trim() : "GENERAL");
        coupon.setDiscountType(StringUtils.hasText(coupon.getDiscountType()) ? coupon.getDiscountType().trim() : "AMOUNT");
        coupon.setDiscountValue(coupon.getDiscountValue() == null ? BigDecimal.ZERO : coupon.getDiscountValue());
        coupon.setMinAmount(coupon.getMinAmount() == null ? BigDecimal.ZERO : coupon.getMinAmount());
        coupon.setTotalCount(coupon.getTotalCount() == null ? 0 : coupon.getTotalCount());
        coupon.setUsedCount(coupon.getUsedCount() == null ? 0 : coupon.getUsedCount());
        coupon.setStatus(coupon.getStatus() == null ? 1 : coupon.getStatus());
        if (coupon.getStartTime() == null || coupon.getEndTime() == null || !coupon.getEndTime().isAfter(coupon.getStartTime())) {
            throw new BusinessException("优惠券时间不合法");
        }
        if (coupon.getId() == null) {
            couponMapper.insert(coupon);
        } else {
            if (couponMapper.selectById(coupon.getId()) == null) {
                throw new BusinessException("优惠券不存在");
            }
            couponMapper.updateById(coupon);
        }
    }

    public void deleteById(Long id) {
        couponMapper.deleteById(id);
    }

    public void receive(Long userId, Long couponId) {
        Coupon coupon = couponMapper.selectById(couponId);
        if (coupon == null || coupon.getStatus() == null || coupon.getStatus() == 0) {
            throw new BusinessException("优惠券不可领取");
        }
        LocalDateTime now = LocalDateTime.now();
        if (coupon.getStartTime() == null || coupon.getEndTime() == null || now.isBefore(coupon.getStartTime()) || now.isAfter(coupon.getEndTime())) {
            throw new BusinessException("优惠券不在有效领取期");
        }
        UserCoupon exist = userCouponMapper.selectOne(new LambdaQueryWrapper<UserCoupon>()
                .eq(UserCoupon::getUserId, userId)
                .eq(UserCoupon::getCouponId, couponId)
                .eq(UserCoupon::getStatus, "UNUSED")
                .last("limit 1"));
        if (exist != null) {
            throw new BusinessException("已领取该优惠券");
        }
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(couponId);
        userCoupon.setStatus("UNUSED");
        userCoupon.setExpireTime(coupon.getEndTime());
        userCouponMapper.insert(userCoupon);
    }

    public List<Map<String, Object>> myCoupons(Long userId, String status) {
        List<UserCoupon> list = userCouponMapper.selectList(new LambdaQueryWrapper<UserCoupon>()
                .eq(UserCoupon::getUserId, userId)
                .eq(StringUtils.hasText(status), UserCoupon::getStatus, status == null ? null : status.trim())
                .orderByDesc(UserCoupon::getId));
        return joinCouponInfo(list);
    }

    public List<Map<String, Object>> availableForOrder(Long userId, BigDecimal amount) {
        BigDecimal total = amount == null ? BigDecimal.ZERO : amount;
        LocalDateTime now = LocalDateTime.now();
        List<UserCoupon> coupons = userCouponMapper.selectList(new LambdaQueryWrapper<UserCoupon>()
                .eq(UserCoupon::getUserId, userId)
                .eq(UserCoupon::getStatus, "UNUSED")
                .ge(UserCoupon::getExpireTime, now)
                .orderByDesc(UserCoupon::getId));
        List<Map<String, Object>> data = joinCouponInfo(coupons);
        return data.stream().filter(item -> {
            Object minAmount = item.get("minAmount");
            BigDecimal min = minAmount == null ? BigDecimal.ZERO : new BigDecimal(String.valueOf(minAmount));
            return total.compareTo(min) >= 0;
        }).collect(Collectors.toList());
    }

    public UserCoupon getUserCouponForUse(Long userId, Long userCouponId, BigDecimal totalAmount) {
        UserCoupon userCoupon = userCouponMapper.selectById(userCouponId);
        if (userCoupon == null || !Objects.equals(userCoupon.getUserId(), userId)) {
            throw new BusinessException("优惠券不存在");
        }
        if (!"UNUSED".equals(userCoupon.getStatus())) {
            throw new BusinessException("优惠券不可用");
        }
        if (userCoupon.getExpireTime() != null && LocalDateTime.now().isAfter(userCoupon.getExpireTime())) {
            throw new BusinessException("优惠券已过期");
        }
        Coupon coupon = couponMapper.selectById(userCoupon.getCouponId());
        if (coupon == null || coupon.getStatus() == null || coupon.getStatus() == 0) {
            throw new BusinessException("优惠券不可用");
        }
        if (coupon.getStartTime() != null && LocalDateTime.now().isBefore(coupon.getStartTime())) {
            throw new BusinessException("优惠券未生效");
        }
        if (coupon.getEndTime() != null && LocalDateTime.now().isAfter(coupon.getEndTime())) {
            throw new BusinessException("优惠券已失效");
        }
        BigDecimal total = totalAmount == null ? BigDecimal.ZERO : totalAmount;
        BigDecimal min = coupon.getMinAmount() == null ? BigDecimal.ZERO : coupon.getMinAmount();
        if (total.compareTo(min) < 0) {
            throw new BusinessException("订单金额未达到优惠券门槛");
        }
        return userCoupon;
    }

    public Coupon getCouponById(Long id) {
        Coupon coupon = couponMapper.selectById(id);
        if (coupon == null) {
            throw new BusinessException("优惠券不存在");
        }
        return coupon;
    }

    public BigDecimal calcDiscount(Coupon coupon, BigDecimal totalAmount) {
        if (coupon == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = totalAmount == null ? BigDecimal.ZERO : totalAmount;
        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        if ("RATE".equalsIgnoreCase(coupon.getDiscountType())) {
            BigDecimal rate = coupon.getDiscountValue() == null ? BigDecimal.ONE : coupon.getDiscountValue();
            if (rate.compareTo(BigDecimal.ZERO) <= 0 || rate.compareTo(BigDecimal.ONE) > 0) {
                return BigDecimal.ZERO;
            }
            BigDecimal pay = total.multiply(rate).setScale(2, RoundingMode.HALF_UP);
            return total.subtract(pay).max(BigDecimal.ZERO);
        }
        BigDecimal amount = coupon.getDiscountValue() == null ? BigDecimal.ZERO : coupon.getDiscountValue();
        return amount.min(total);
    }

    public void markUsed(Long userId, Long userCouponId) {
        UserCoupon userCoupon = userCouponMapper.selectById(userCouponId);
        if (userCoupon == null || !Objects.equals(userCoupon.getUserId(), userId)) {
            return;
        }
        if (!"UNUSED".equals(userCoupon.getStatus())) {
            return;
        }
        userCoupon.setStatus("USED");
        userCoupon.setUseTime(LocalDateTime.now());
        userCouponMapper.updateById(userCoupon);
        Coupon coupon = couponMapper.selectById(userCoupon.getCouponId());
        if (coupon != null) {
            coupon.setUsedCount((coupon.getUsedCount() == null ? 0 : coupon.getUsedCount()) + 1);
            couponMapper.updateById(coupon);
        }
    }

    public void rollbackUsed(Long userId, Long userCouponId) {
        UserCoupon userCoupon = userCouponMapper.selectById(userCouponId);
        if (userCoupon == null || !Objects.equals(userCoupon.getUserId(), userId)) {
            return;
        }
        if (!"USED".equals(userCoupon.getStatus())) {
            return;
        }
        userCoupon.setStatus("UNUSED");
        userCoupon.setUseTime(null);
        userCouponMapper.updateById(userCoupon);
        Coupon coupon = couponMapper.selectById(userCoupon.getCouponId());
        if (coupon != null) {
            int used = coupon.getUsedCount() == null ? 0 : coupon.getUsedCount();
            coupon.setUsedCount(Math.max(0, used - 1));
            couponMapper.updateById(coupon);
        }
    }

    private List<Map<String, Object>> joinCouponInfo(List<UserCoupon> userCoupons) {
        if (userCoupons == null || userCoupons.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        Set<Long> couponIds = userCoupons.stream().map(UserCoupon::getCouponId).collect(Collectors.toSet());
        Map<Long, Coupon> couponMap = couponMapper.selectBatchIds(couponIds).stream()
                .collect(Collectors.toMap(Coupon::getId, e -> e));
        return userCoupons.stream().map(item -> {
            Coupon coupon = couponMap.get(item.getCouponId());
            Map<String, Object> map = new HashMap<>();
            map.put("userCouponId", item.getId());
            map.put("userId", item.getUserId());
            map.put("couponId", item.getCouponId());
            map.put("status", item.getStatus());
            map.put("useTime", item.getUseTime());
            map.put("expireTime", item.getExpireTime());
            if (coupon != null) {
                map.put("name", coupon.getName());
                map.put("type", coupon.getType());
                map.put("discountType", coupon.getDiscountType());
                map.put("discountValue", coupon.getDiscountValue());
                map.put("minAmount", coupon.getMinAmount());
                map.put("couponStatus", coupon.getStatus());
                map.put("startTime", coupon.getStartTime());
                map.put("endTime", coupon.getEndTime());
            }
            return map;
        }).collect(Collectors.toList());
    }
}
