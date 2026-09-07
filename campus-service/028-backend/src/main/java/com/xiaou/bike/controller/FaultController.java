package com.xiaou.bike.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.bike.common.Result;
import com.xiaou.bike.dto.FaultReportDTO;
import com.xiaou.bike.entity.FaultReport;
import com.xiaou.bike.service.FaultReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 故障上报控制器
 */
@RestController
@RequestMapping("/fault")
@RequiredArgsConstructor
public class FaultController {

    private final FaultReportService faultReportService;

    /**
     * 上报故障
     */
    @PostMapping("/report")
    public Result<Void> report(@RequestBody @Valid FaultReportDTO dto) {
        faultReportService.report(dto);
        return Result.success();
    }

    /**
     * 获取用户上报记录
     */
    @GetMapping("/my-reports")
    public Result<Page<FaultReport>> getMyReports(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Page<FaultReport> result = faultReportService.getUserReports(page, size);
        return Result.success(result);
    }
}
