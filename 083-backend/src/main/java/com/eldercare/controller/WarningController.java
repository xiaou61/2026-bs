package com.eldercare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eldercare.common.Result;
import com.eldercare.entity.AbnormalWarning;
import com.eldercare.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warning")
public class WarningController {

    @Autowired
    private WarningService warningService;

    @GetMapping("/list")
    public Result<Page<AbnormalWarning>> list(@RequestParam(defaultValue = "1") int pageNum,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              @RequestParam(required = false) Long elderId,
                                              @RequestParam(required = false) String warningLevel,
                                              @RequestParam(required = false) Integer status) {
        return Result.success(warningService.page(pageNum, pageSize, elderId, warningLevel, status));
    }

    @PutMapping("/status")
    public Result<String> status(@RequestParam Long id, @RequestParam Integer status) {
        warningService.updateStatus(id, status);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id) {
        warningService.delete(id);
        return Result.success();
    }
}
