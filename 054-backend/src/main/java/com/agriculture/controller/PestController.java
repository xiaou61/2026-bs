package com.agriculture.controller;

import com.agriculture.common.Result;
import com.agriculture.entity.PestDisease;
import com.agriculture.entity.PestWarning;
import com.agriculture.entity.TreatmentRecord;
import com.agriculture.service.PestService;
import com.agriculture.service.TreatmentService;
import com.agriculture.service.WarningService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pest")
public class PestController {

    @Autowired
    private PestService pestService;

    @Autowired
    private WarningService warningService;

    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("/page")
    public Result<Page<PestDisease>> getPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) Integer type) {
        return Result.success(pestService.getPage(pageNum, pageSize, name, type));
    }

    @GetMapping("/{id}")
    public Result<PestDisease> getById(@PathVariable Long id) {
        return Result.success(pestService.getById(id));
    }

    @PostMapping
    public Result<?> add(@RequestBody PestDisease pest) {
        pestService.save(pest);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody PestDisease pest) {
        pestService.updateById(pest);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        pestService.removeById(id);
        return Result.success();
    }

    @GetMapping("/warning/page")
    public Result<Page<PestWarning>> getWarningPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                                     @RequestParam(required = false) String region,
                                                     @RequestParam(required = false) Integer warningLevel) {
        return Result.success(warningService.getPage(pageNum, pageSize, region, warningLevel));
    }

    @PostMapping("/warning")
    public Result<?> addWarning(@RequestBody PestWarning warning, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        warning.setPublisherId(userId);
        warningService.save(warning);
        return Result.success();
    }

    @PutMapping("/warning")
    public Result<?> updateWarning(@RequestBody PestWarning warning) {
        warningService.updateById(warning);
        return Result.success();
    }

    @DeleteMapping("/warning/{id}")
    public Result<?> deleteWarning(@PathVariable Long id) {
        warningService.removeById(id);
        return Result.success();
    }

    @GetMapping("/treatment/page")
    public Result<Page<TreatmentRecord>> getTreatmentPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                                           @RequestParam(required = false) Long planId) {
        return Result.success(treatmentService.getPage(pageNum, pageSize, planId));
    }

    @PostMapping("/treatment")
    public Result<?> addTreatment(@RequestBody TreatmentRecord record, HttpServletRequest request) {
        Long userId = Long.parseLong(request.getAttribute("userId").toString());
        record.setOperatorId(userId);
        treatmentService.save(record);
        return Result.success();
    }

    @PutMapping("/treatment")
    public Result<?> updateTreatment(@RequestBody TreatmentRecord record) {
        treatmentService.updateById(record);
        return Result.success();
    }
}
