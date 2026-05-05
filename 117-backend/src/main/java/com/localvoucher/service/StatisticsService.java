package com.localvoucher.service;

import com.localvoucher.mapper.ActivityStatRecordMapper;
import com.localvoucher.mapper.ComplaintTicketMapper;
import com.localvoucher.mapper.CouponActivityMapper;
import com.localvoucher.mapper.CouponTemplateMapper;
import com.localvoucher.mapper.MerchantSettlementMapper;
import com.localvoucher.mapper.PaymentTransferMapper;
import com.localvoucher.mapper.UserCouponMapper;
import com.localvoucher.mapper.VerificationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final CouponTemplateMapper templateMapper;
    private final CouponActivityMapper activityMapper;
    private final UserCouponMapper couponMapper;
    private final VerificationRecordMapper verificationMapper;
    private final MerchantSettlementMapper settlementMapper;
    private final PaymentTransferMapper transferMapper;
    private final ComplaintTicketMapper complaintMapper;
    private final ActivityStatRecordMapper statMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("templateCount", templateMapper.selectCount(null));
        data.put("activityCount", activityMapper.selectCount(null));
        data.put("couponCount", couponMapper.selectCount(null));
        data.put("verificationCount", verificationMapper.selectCount(null));
        data.put("settlementCount", settlementMapper.selectCount(null));
        data.put("transferCount", transferMapper.selectCount(null));
        data.put("complaintCount", complaintMapper.selectCount(null));
        data.put("statCount", statMapper.selectCount(null));
        data.put("verifyTrend", Arrays.asList(18, 26, 32, 45, 51, 64, 78));
        data.put("industryPie", Arrays.asList(map("餐饮美食", 42), map("休闲娱乐", 24), map("丽人美业", 20), map("亲子教育", 14)));
        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }
}
