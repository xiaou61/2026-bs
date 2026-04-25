package com.xiaou.rice.modules.farm.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.rice.common.api.Result;
import com.xiaou.rice.common.api.ResultCode;
import com.xiaou.rice.common.exception.BusinessException;
import com.xiaou.rice.modules.farm.dto.PlotRequest;
import com.xiaou.rice.modules.farm.entity.FarmPlot;
import com.xiaou.rice.modules.farm.service.FarmPlotService;
import com.xiaou.rice.security.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plots")
public class FarmPlotController {

    private static final int ROLE_FARMER = 1;

    private final FarmPlotService farmPlotService;

    public FarmPlotController(FarmPlotService farmPlotService) {
        this.farmPlotService = farmPlotService;
    }

    @GetMapping
    public Result<List<FarmPlot>> list() {
        requireFarmer();
        Long uid = SecurityUtil.currentUserId();
        List<FarmPlot> plots = farmPlotService.list(new LambdaQueryWrapper<FarmPlot>()
                .eq(FarmPlot::getUserId, uid)
                .orderByDesc(FarmPlot::getCreatedAt));
        return Result.ok(plots);
    }

    @PostMapping
    public Result<FarmPlot> create(@Valid @RequestBody PlotRequest req) {
        requireFarmer();
        Long uid = SecurityUtil.currentUserId();
        FarmPlot plot = new FarmPlot();
        plot.setUserId(uid);
        plot.setName(req.getName());
        plot.setArea(req.getArea());
        plot.setCropVariety(req.getCropVariety());
        plot.setGrowthStage(req.getGrowthStage());
        plot.setLocation(req.getLocation());
        plot.setLatitude(req.getLatitude());
        plot.setLongitude(req.getLongitude());
        plot.setPhotos(req.getPhotos());
        plot.setRemark(req.getRemark());
        farmPlotService.save(plot);
        return Result.ok(plot);
    }

    @PutMapping("/{id}")
    public Result<Boolean> update(@PathVariable Long id, @Valid @RequestBody PlotRequest req) {
        requireFarmer();
        Long uid = SecurityUtil.currentUserId();
        FarmPlot plot = farmPlotService.getById(id);
        if (plot == null || !plot.getUserId().equals(uid)) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "地块不存在或无权限");
        }
        plot.setName(req.getName());
        plot.setArea(req.getArea());
        plot.setCropVariety(req.getCropVariety());
        plot.setGrowthStage(req.getGrowthStage());
        plot.setLocation(req.getLocation());
        plot.setLatitude(req.getLatitude());
        plot.setLongitude(req.getLongitude());
        plot.setPhotos(req.getPhotos());
        plot.setRemark(req.getRemark());
        return Result.ok(farmPlotService.updateById(plot));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        requireFarmer();
        Long uid = SecurityUtil.currentUserId();
        FarmPlot plot = farmPlotService.getById(id);
        if (plot == null || !plot.getUserId().equals(uid)) {
            throw new BusinessException(ResultCode.NOT_FOUND.getCode(), "地块不存在或无权限");
        }
        return Result.ok(farmPlotService.removeById(id));
    }

    private void requireFarmer() {
        if (!Integer.valueOf(ROLE_FARMER).equals(SecurityUtil.currentRole())) {
            throw new BusinessException(ResultCode.FORBIDDEN.getCode(), "仅农户可操作地块");
        }
    }
}
