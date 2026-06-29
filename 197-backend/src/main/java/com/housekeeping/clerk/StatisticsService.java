package com.housekeeping.clerk;

import com.housekeeping.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final ServiceStationMapper serviceStationMapper;
    private final ResidentProfileMapper residentProfileMapper;
    private final WorkerProfileMapper workerProfileMapper;
    private final ServiceCatalogMapper serviceCatalogMapper;
    private final ServiceBookingMapper serviceBookingMapper;
    private final ServiceRecordMapper serviceRecordMapper;
    private final ScheduleDispatchMapper scheduleDispatchMapper;
    private final BookingReviewMapper bookingReviewMapper;
    private final CreditEvaluationMapper creditEvaluationMapper;
    private final ComplaintHandlingMapper complaintHandlingMapper;
    private final SettlementRecordMapper settlementRecordMapper;
    private final OperationLogMapper operationLogMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        long stationCount = serviceStationMapper.countAll();
        long residentCount = residentProfileMapper.countAll();
        long workerCount = workerProfileMapper.countAll();
        long serviceCount = serviceCatalogMapper.countAll();
        long bookingCount = serviceBookingMapper.countAll();
        long recordCount = serviceRecordMapper.countAll();
        long dispatchCount = scheduleDispatchMapper.countAll();
        long reviewCount = bookingReviewMapper.countAll();
        long evaluationCount = creditEvaluationMapper.countAll();
        long complaintCount = complaintHandlingMapper.countAll();
        long settlementCount = settlementRecordMapper.countAll();
        long logCount = operationLogMapper.countAll();
        long totalCount = stationCount + residentCount + workerCount + serviceCount + bookingCount + recordCount + dispatchCount + reviewCount + evaluationCount + complaintCount + settlementCount + logCount;

        data.put("stationCount", stationCount);
        data.put("residentCount", residentCount);
        data.put("workerCount", workerCount);
        data.put("serviceCount", serviceCount);
        data.put("bookingCount", bookingCount);
        data.put("recordCount", recordCount);
        data.put("dispatchCount", dispatchCount);
        data.put("reviewCount", reviewCount);
        data.put("evaluationCount", evaluationCount);
        data.put("complaintCount", complaintCount);
        data.put("settlementCount", settlementCount);
        data.put("logCount", logCount);
        data.put("totalCount", totalCount);

        data.put("bookings", Arrays.asList(
            mapTrend("周一", 12),
            mapTrend("周二", 18),
            mapTrend("周三", 25),
            mapTrend("周四", 22),
            mapTrend("周五", 30),
            mapTrend("周六", 35),
            mapTrend("周日", 28)
        ));
        data.put("evaluations", Arrays.asList(
            mapTrend("周一", 5),
            mapTrend("周二", 8),
            mapTrend("周三", 12),
            mapTrend("周四", 10),
            mapTrend("周五", 15),
            mapTrend("周六", 18),
            mapTrend("周日", 14)
        ));

        data.put("pie", Arrays.asList(
            map("已预约", 36),
            map("待派单", 24),
            map("服务中", 32),
            map("已评价", 28),
            map("投诉中", 12)
        ));

        data.put("categoryPie", Arrays.asList(
            map("保洁服务", 45),
            map("月嫂服务", 20),
            map("育儿服务", 18),
            map("养老陪护", 12),
            map("家电清洗", 5)
        ));

        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }

    private Map<String, Object> mapTrend(String day, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("day", day);
        item.put("value", value);
        return item;
    }
}
