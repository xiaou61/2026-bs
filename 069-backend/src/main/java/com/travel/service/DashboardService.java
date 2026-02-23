package com.teacher.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.teacher.entity.TravelOrder;
import com.teacher.entity.TravelReview;
import com.teacher.entity.Traveler;
import com.teacher.entity.User;
import com.teacher.entity.UserComplaint;
import com.teacher.entity.UserFavorite;
import com.teacher.mapper.TravelOrderMapper;
import com.teacher.mapper.TravelReviewMapper;
import com.teacher.mapper.TravelerMapper;
import com.teacher.mapper.UserComplaintMapper;
import com.teacher.mapper.UserFavoriteMapper;
import com.teacher.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private TravelOrderMapper travelOrderMapper;

    @Resource
    private TravelReviewMapper travelReviewMapper;

    @Resource
    private UserComplaintMapper userComplaintMapper;

    @Resource
    private UserFavoriteMapper userFavoriteMapper;

    @Resource
    private TravelerMapper travelerMapper;

    @Resource
    private SpotService spotService;

    public Map<String, Object> stats(Long userId, String role) {
        Map<String, Object> map = new HashMap<>();
        if ("ADMIN".equals(role)) {
            map.put("userCount", userMapper.selectCount(new LambdaQueryWrapper<User>()));
            map.put("orderCount", travelOrderMapper.selectCount(new LambdaQueryWrapper<TravelOrder>()));
            map.put("reviewCount", travelReviewMapper.selectCount(new LambdaQueryWrapper<TravelReview>()));
            map.put("pendingComplaintCount", userComplaintMapper.selectCount(new LambdaQueryWrapper<UserComplaint>().eq(UserComplaint::getStatus, ComplaintService.STATUS_WAITING)));
            map.put("spotCount", spotService.countAll());
            return map;
        }
        map.put("myOrderCount", travelOrderMapper.selectCount(new LambdaQueryWrapper<TravelOrder>().eq(TravelOrder::getUserId, userId)));
        map.put("myWaitPayCount", travelOrderMapper.selectCount(new LambdaQueryWrapper<TravelOrder>().eq(TravelOrder::getUserId, userId).eq(TravelOrder::getStatus, OrderService.STATUS_WAIT_PAY)));
        map.put("myFinishedCount", travelOrderMapper.selectCount(new LambdaQueryWrapper<TravelOrder>().eq(TravelOrder::getUserId, userId).eq(TravelOrder::getStatus, OrderService.STATUS_FINISHED)));
        map.put("myFavoriteCount", userFavoriteMapper.selectCount(new LambdaQueryWrapper<UserFavorite>().eq(UserFavorite::getUserId, userId)));
        map.put("myComplaintCount", userComplaintMapper.selectCount(new LambdaQueryWrapper<UserComplaint>().eq(UserComplaint::getUserId, userId)));
        map.put("myTravelerCount", travelerMapper.selectCount(new LambdaQueryWrapper<Traveler>().eq(Traveler::getUserId, userId)));
        return map;
    }

    public Map<String, Object> trend(Long userId, String role) {
        LocalDate startDay = LocalDate.now().minusDays(6);
        LocalDateTime start = startDay.atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        List<TravelOrder> orders = travelOrderMapper.selectList(new LambdaQueryWrapper<TravelOrder>()
                .ge(TravelOrder::getCreateTime, start)
                .lt(TravelOrder::getCreateTime, end)
                .eq("USER".equals(role), TravelOrder::getUserId, userId));
        Map<String, Long> dayCountMap = new HashMap<>();
        Map<String, BigDecimal> dayAmountMap = new HashMap<>();
        Map<String, Long> statusCountMap = new HashMap<>();
        for (TravelOrder order : orders) {
            String day = order.getCreateTime() == null ? LocalDate.now().toString() : order.getCreateTime().toLocalDate().toString();
            dayCountMap.put(day, dayCountMap.getOrDefault(day, 0L) + 1L);
            BigDecimal amount = order.getTotalAmount() == null ? BigDecimal.ZERO : order.getTotalAmount();
            dayAmountMap.put(day, dayAmountMap.getOrDefault(day, BigDecimal.ZERO).add(amount));
            String status = order.getStatus() == null ? "" : order.getStatus();
            statusCountMap.put(status, statusCountMap.getOrDefault(status, 0L) + 1L);
        }

        List<Map<String, Object>> daily = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            String day = LocalDate.now().minusDays(i).toString();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("day", day);
            item.put("orderCount", dayCountMap.getOrDefault(day, 0L));
            item.put("totalAmount", dayAmountMap.getOrDefault(day, BigDecimal.ZERO));
            daily.add(item);
        }

        List<Map<String, Object>> statusList = new ArrayList<>();
        String[] statuses = {OrderService.STATUS_WAIT_PAY, OrderService.STATUS_PAID, OrderService.STATUS_FINISHED, OrderService.STATUS_CANCELED};
        for (String status : statuses) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("status", status);
            item.put("count", statusCountMap.getOrDefault(status, 0L));
            statusList.add(item);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("daily", daily);
        result.put("status", statusList);
        return result;
    }
}

