package com.heritageworkshop.clerk;

import com.heritageworkshop.mapper.WorkshopProfileMapper;
import com.heritageworkshop.mapper.InheritorProfileMapper;
import com.heritageworkshop.mapper.CourseCatalogMapper;
import com.heritageworkshop.mapper.CourseScheduleMapper;
import com.heritageworkshop.mapper.CourseBookingMapper;
import com.heritageworkshop.mapper.ArtworkCatalogMapper;
import com.heritageworkshop.mapper.ExhibitionShowcaseMapper;
import com.heritageworkshop.mapper.ProductOrderMapper;
import com.heritageworkshop.mapper.SalesSettlementMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final WorkshopProfileMapper workshopProfileMapper;
    private final InheritorProfileMapper inheritorProfileMapper;
    private final CourseCatalogMapper courseCatalogMapper;
    private final CourseScheduleMapper courseScheduleMapper;
    private final CourseBookingMapper courseBookingMapper;
    private final ArtworkCatalogMapper artworkCatalogMapper;
    private final ExhibitionShowcaseMapper exhibitionShowcaseMapper;
    private final ProductOrderMapper productOrderMapper;
    private final SalesSettlementMapper salesSettlementMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> data = new HashMap<>();
        long workshopCount = workshopProfileMapper.countAll();
        long inheritorCount = inheritorProfileMapper.countAll();
        long courseCount = courseCatalogMapper.countAll();
        long scheduleCount = courseScheduleMapper.countAll();
        long bookingCount = courseBookingMapper.countAll();
        long artworkCount = artworkCatalogMapper.countAll();
        long showcaseCount = exhibitionShowcaseMapper.countAll();
        long orderCount = productOrderMapper.countAll();
        long settlementCount = salesSettlementMapper.countAll();

        data.put("workshopCount", workshopCount);
        data.put("inheritorCount", inheritorCount);
        data.put("courseCount", courseCount);
        data.put("scheduleCount", scheduleCount);
        data.put("bookingCount", bookingCount);
        data.put("artworkCount", artworkCount);
        data.put("showcaseCount", showcaseCount);
        data.put("orderCount", orderCount);
        data.put("settlementCount", settlementCount);
        data.put("totalCount", workshopCount + inheritorCount + courseCount + scheduleCount + bookingCount + artworkCount + showcaseCount + orderCount + settlementCount);

        data.put("trend", Arrays.asList(
            mapTrend("周一", 31, 18),
            mapTrend("周二", 46, 25),
            mapTrend("周三", 58, 32),
            mapTrend("周四", 72, 41),
            mapTrend("周五", 86, 53),
            mapTrend("周六", 101, 67),
            mapTrend("周日", 119, 78)
        ));

        data.put("pie", Arrays.asList(
            map("已建档", 36),
            map("已排期", 24),
            map("预约中", 32),
            map("展销中", 28),
            map("结算中", 12),
            map("已闭环", 18)
        ));

        data.put("categoryPie", Arrays.asList(
            map("工坊档案", (int) workshopCount),
            map("传承人", (int) inheritorCount),
            map("课程目录", (int) courseCount),
            map("工坊排期", (int) scheduleCount),
            map("课程预约", (int) bookingCount),
            map("作品档案", (int) artworkCount),
            map("作品展销", (int) showcaseCount),
            map("展销订单", (int) orderCount),
            map("展销结算", (int) settlementCount)
        ));

        return data;
    }

    private Map<String, Object> map(String name, Integer value) {
        Map<String, Object> item = new HashMap<>();
        item.put("name", name);
        item.put("value", value);
        return item;
    }

    private Map<String, Object> mapTrend(String day, int bookings, int sales) {
        Map<String, Object> item = new HashMap<>();
        item.put("day", day);
        item.put("bookings", bookings);
        item.put("sales", sales);
        return item;
    }
}
