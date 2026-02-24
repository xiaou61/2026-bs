package com.harbin.tourism.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.entity.Route;
import com.harbin.tourism.entity.RouteSpot;
import com.harbin.tourism.entity.ScenicSpot;
import com.harbin.tourism.mapper.RouteMapper;
import com.harbin.tourism.mapper.RouteSpotMapper;
import com.harbin.tourism.mapper.ScenicSpotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteService {

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private RouteSpotMapper routeSpotMapper;

    @Autowired
    private ScenicSpotMapper spotMapper;

    public Page<Route> page(Integer pageNum, Integer pageSize, String keyword, Integer days, String category) {
        LambdaQueryWrapper<Route> wrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(Route::getTitle, keyword);
        }
        if (days != null) {
            wrapper.eq(Route::getDays, days);
        }
        if (StrUtil.isNotBlank(category)) {
            wrapper.eq(Route::getCategory, category);
        }
        wrapper.eq(Route::getStatus, 1);
        wrapper.orderByDesc(Route::getLikeCount);
        return routeMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    }

    public Map<String, Object> detail(Long id) {
        Route route = routeMapper.selectById(id);
        List<RouteSpot> routeSpots = routeSpotMapper.selectList(
                new LambdaQueryWrapper<RouteSpot>()
                        .eq(RouteSpot::getRouteId, id)
                        .orderByAsc(RouteSpot::getOrderNum)
        );
        List<Map<String, Object>> spotList = new ArrayList<>();
        for (RouteSpot rs : routeSpots) {
            ScenicSpot spot = spotMapper.selectById(rs.getSpotId());
            Map<String, Object> item = new HashMap<>();
            item.put("spot", spot);
            item.put("orderNum", rs.getOrderNum());
            item.put("stayHours", rs.getStayHours());
            spotList.add(item);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("route", route);
        result.put("spots", spotList);
        return result;
    }

    public Route getById(Long id) {
        return routeMapper.selectById(id);
    }

    @Transactional
    public void save(Route route, List<RouteSpot> spots) {
        route.setLikeCount(0);
        route.setStatus(1);
        routeMapper.insert(route);
        for (RouteSpot spot : spots) {
            spot.setRouteId(route.getId());
            routeSpotMapper.insert(spot);
        }
    }

    @Transactional
    public void update(Route route, List<RouteSpot> spots) {
        routeMapper.updateById(route);
        routeSpotMapper.delete(new LambdaQueryWrapper<RouteSpot>()
                .eq(RouteSpot::getRouteId, route.getId()));
        for (RouteSpot spot : spots) {
            spot.setRouteId(route.getId());
            routeSpotMapper.insert(spot);
        }
    }

    public void delete(Long id) {
        routeMapper.deleteById(id);
        routeSpotMapper.delete(new LambdaQueryWrapper<RouteSpot>()
                .eq(RouteSpot::getRouteId, id));
    }

    public void like(Long id) {
        Route route = routeMapper.selectById(id);
        route.setLikeCount(route.getLikeCount() + 1);
        routeMapper.updateById(route);
    }

    public long count() {
        return routeMapper.selectCount(new LambdaQueryWrapper<Route>()
                .eq(Route::getStatus, 1));
    }
}
