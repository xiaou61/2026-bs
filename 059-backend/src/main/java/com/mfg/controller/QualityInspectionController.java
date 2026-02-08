package com.mfg.controller;

import com.mfg.common.Result;
import com.mfg.entity.QualityInspection;
import com.mfg.service.QualityInspectionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/qualityInspection")
public class QualityInspectionController {

    @Resource
    private QualityInspectionService qualityInspectionService;

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String result,
                          @RequestParam(required = false) Long orderId) {
        return Result.success(qualityInspectionService.page(pageNum, pageSize, result, orderId));
    }

    @PostMapping
    public Result<?> add(@RequestBody QualityInspection inspection) {
        qualityInspectionService.add(inspection);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody QualityInspection inspection) {
        qualityInspectionService.update(inspection);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        qualityInspectionService.delete(id);
        return Result.success();
    }
}
