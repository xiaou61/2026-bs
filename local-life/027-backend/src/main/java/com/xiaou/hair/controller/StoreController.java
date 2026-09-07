package com.xiaou.hair.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.hair.common.Result;
import com.xiaou.hair.entity.Hairdresser;
import com.xiaou.hair.entity.ServiceEntity;
import com.xiaou.hair.entity.Store;
import com.xiaou.hair.service.HairdresserService;
import com.xiaou.hair.service.ServiceService;
import com.xiaou.hair.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门店控制器
 */
@RestController
@RequestMapping("/api/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private HairdresserService hairdresserService;

    @Autowired
    private ServiceService serviceService;

    /**
     * 门店列表
     */
    @GetMapping("/list")
    public Result<Page<Store>> getStoreList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortBy) {
        Page<Store> page = storeService.getStoreList(pageNum, pageSize, keyword, sortBy);
        return Result.success(page);
    }

    /**
     * 门店详情
     */
    @GetMapping("/{id}")
    public Result<Store> getStoreDetail(@PathVariable Long id) {
        Store store = storeService.getStoreById(id);
        return Result.success(store);
    }

    /**
     * 门店的理发师列表
     */
    @GetMapping("/{id}/hairdressers")
    public Result<List<Hairdresser>> getStoreHairdressers(@PathVariable Long id) {
        List<Hairdresser> hairdressers = hairdresserService.getHairdressersByStoreId(id);
        return Result.success(hairdressers);
    }

    /**
     * 门店的服务项目列表
     */
    @GetMapping("/{id}/services")
    public Result<List<ServiceEntity>> getStoreServices(@PathVariable Long id) {
        List<ServiceEntity> services = serviceService.getServicesByStoreId(id);
        return Result.success(services);
    }
}
