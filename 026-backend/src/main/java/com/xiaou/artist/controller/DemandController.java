package com.xiaou.artist.controller;

import com.xiaou.artist.common.Result;
import com.xiaou.artist.entity.Demand;
import com.xiaou.artist.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/demand")
public class DemandController {
    
    @Autowired
    private DemandService demandService;
    
    @PostMapping("/create")
    public Result<Demand> createDemand(@RequestBody Demand demand) {
        Demand newDemand = demandService.createDemand(demand);
        return Result.success(newDemand);
    }
    
    @GetMapping("/user/{userId}")
    public Result<List<Demand>> getDemandsByUserId(@PathVariable Long userId) {
        List<Demand> demands = demandService.getDemandsByUserId(userId);
        return Result.success(demands);
    }
    
    @GetMapping("/list")
    public Result<List<Demand>> getAllDemands() {
        List<Demand> demands = demandService.getAllDemands();
        return Result.success(demands);
    }
    
    @GetMapping("/open")
    public Result<List<Demand>> getOpenDemands() {
        List<Demand> demands = demandService.getOpenDemands();
        return Result.success(demands);
    }
    
    @GetMapping("/{id}")
    public Result<Demand> getDemandById(@PathVariable Long id) {
        Demand demand = demandService.getDemandById(id);
        return Result.success(demand);
    }
    
    @PutMapping("/update")
    public Result<String> updateDemand(@RequestBody Demand demand) {
        boolean success = demandService.updateDemand(demand);
        return success ? Result.success("更新成功") : Result.error("更新失败");
    }
    
    @PutMapping("/close/{id}")
    public Result<String> closeDemand(@PathVariable Long id) {
        boolean success = demandService.closeDemand(id);
        return success ? Result.success("需求已关闭") : Result.error("操作失败");
    }
    
    @DeleteMapping("/{id}")
    public Result<String> deleteDemand(@PathVariable Long id) {
        boolean success = demandService.deleteDemand(id);
        return success ? Result.success("删除成功") : Result.error("删除失败");
    }
}
