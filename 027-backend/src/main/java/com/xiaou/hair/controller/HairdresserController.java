package com.xiaou.hair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.common.Result;
import com.xiaou.hair.entity.Hairdresser;
import com.xiaou.hair.service.HairdresserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 理发师控制器
 */
@RestController
@RequestMapping("/api/hairdresser")
public class HairdresserController {

    @Autowired
    private HairdresserService hairdresserService;

    /**
     * 理发师列表
     */
    @GetMapping("/list")
    public Result<Page<Hairdresser>> getHairdresserList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) String sortBy) {
        Page<Hairdresser> page = hairdresserService.getHairdresserList(pageNum, pageSize, storeId, sortBy);
        return Result.success(page);
    }

    /**
     * 理发师详情
     */
    @GetMapping("/{id}")
    public Result<Hairdresser> getHairdresserDetail(@PathVariable Long id) {
        Hairdresser hairdresser = hairdresserService.getHairdresserById(id);
        return Result.success(hairdresser);
    }
}
