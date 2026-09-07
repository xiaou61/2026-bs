package com.railway.controller;

import com.railway.common.Result;
import com.railway.entity.Station;
import com.railway.service.StationService;
import com.railway.utils.AuthUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/station")
public class StationController {

    @Resource
    private StationService stationService;

    @GetMapping("/list")
    public Result<?> list(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String stationName,
                          @RequestParam(required = false) String city,
                          @RequestParam(required = false) Integer status,
                          HttpServletRequest request) {
        AuthUtils.requireStaff((String) request.getAttribute("role"));
        return Result.success(stationService.page(pageNum, pageSize, stationName, city, status));
    }

    @GetMapping("/enabled")
    public Result<?> enabled() {
        return Result.success(stationService.enabledList());
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Station station, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        stationService.save(station);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        AuthUtils.requireAdmin((String) request.getAttribute("role"));
        stationService.deleteById(id);
        return Result.success();
    }
}
