package com.harbin.tourism.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harbin.tourism.common.Result;
import com.harbin.tourism.entity.Route;
import com.harbin.tourism.entity.RouteSpot;
import com.harbin.tourism.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/list")
    public Result<Page<Route>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer days,
            @RequestParam(required = false) String category) {
        return Result.success(routeService.page(pageNum, pageSize, keyword, days, category));
    }

    @GetMapping("/detail/{id}")
    public Result<Map<String, Object>> detail(@PathVariable Long id) {
        return Result.success(routeService.detail(id));
    }

    @PostMapping
    public Result<Void> add(@RequestBody Map<String, Object> params) {
        Route route = new Route();
        route.setTitle((String) params.get("title"));
        route.setDescription((String) params.get("description"));
        route.setDays((Integer) params.get("days"));
        route.setDifficulty((String) params.get("difficulty"));
        route.setCategory((String) params.get("category"));
        route.setCoverImg((String) params.get("coverImg"));
        route.setEstimatedCost(new java.math.BigDecimal(params.get("estimatedCost").toString()));
        route.setUserId(Long.valueOf(params.get("userId").toString()));
        List<Map<String, Object>> spotsList = (List<Map<String, Object>>) params.get("spots");
        List<RouteSpot> spots = new java.util.ArrayList<>();
        for (Map<String, Object> s : spotsList) {
            RouteSpot rs = new RouteSpot();
            rs.setSpotId(Long.valueOf(s.get("spotId").toString()));
            rs.setOrderNum((Integer) s.get("orderNum"));
            rs.setStayHours(new java.math.BigDecimal(s.get("stayHours").toString()));
            spots.add(rs);
        }
        routeService.save(route, spots);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Map<String, Object> params) {
        Route route = new Route();
        route.setId(Long.valueOf(params.get("id").toString()));
        route.setTitle((String) params.get("title"));
        route.setDescription((String) params.get("description"));
        route.setDays((Integer) params.get("days"));
        route.setDifficulty((String) params.get("difficulty"));
        route.setCategory((String) params.get("category"));
        route.setCoverImg((String) params.get("coverImg"));
        route.setEstimatedCost(new java.math.BigDecimal(params.get("estimatedCost").toString()));
        List<Map<String, Object>> spotsList = (List<Map<String, Object>>) params.get("spots");
        List<RouteSpot> spots = new java.util.ArrayList<>();
        for (Map<String, Object> s : spotsList) {
            RouteSpot rs = new RouteSpot();
            rs.setSpotId(Long.valueOf(s.get("spotId").toString()));
            rs.setOrderNum((Integer) s.get("orderNum"));
            rs.setStayHours(new java.math.BigDecimal(s.get("stayHours").toString()));
            spots.add(rs);
        }
        routeService.update(route, spots);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        routeService.delete(id);
        return Result.success();
    }

    @PostMapping("/like/{id}")
    public Result<Void> like(@PathVariable Long id) {
        routeService.like(id);
        return Result.success();
    }
}
