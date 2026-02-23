package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.entity.Coupon;
import com.ticket.entity.UserCoupon;
import com.ticket.mapper.CouponMapper;
import com.ticket.mapper.UserCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    public Page<Coupon> listCoupons(Integer pageNum, Integer pageSize, Integer status) {
        Page<Coupon> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Coupon> wrapper = new QueryWrapper<>();
        if (status != null) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return couponMapper.selectPage(page, wrapper);
    }

    public List<Coupon> getAvailableCoupons() {
        QueryWrapper<Coupon> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1)
                .le("start_time", LocalDateTime.now())
                .ge("end_time", LocalDateTime.now())
                .apply("total_count > used_count");
        return couponMapper.selectList(wrapper);
    }

    @Transactional
    public void receiveCoupon(Long couponId, Long userId) {
        Coupon coupon = couponMapper.selectById(couponId);
        if (coupon == null) {
            throw new BusinessException("优惠券不存在");
        }

        if (coupon.getTotalCount() <= coupon.getUsedCount()) {
            throw new BusinessException("优惠券已抢光");
        }

        QueryWrapper<UserCoupon> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("coupon_id", couponId);
        UserCoupon existCoupon = userCouponMapper.selectOne(wrapper);
        if (existCoupon != null) {
            throw new BusinessException("已领取过该优惠券");
        }

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserId(userId);
        userCoupon.setCouponId(couponId);
        userCoupon.setStatus("unused");
        userCoupon.setExpireTime(coupon.getEndTime());
        userCouponMapper.insert(userCoupon);

        coupon.setUsedCount(coupon.getUsedCount() + 1);
        couponMapper.updateById(coupon);
    }

    public List<UserCoupon> getMyCoupons(Long userId, String status) {
        QueryWrapper<UserCoupon> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        if (status != null && !status.isEmpty()) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("create_time");
        return userCouponMapper.selectList(wrapper);
    }

    public void addCoupon(Coupon coupon) {
        couponMapper.insert(coupon);
    }

    public void updateCoupon(Coupon coupon) {
        couponMapper.updateById(coupon);
    }

    public void deleteCoupon(Long id) {
        couponMapper.deleteById(id);
    }
}
