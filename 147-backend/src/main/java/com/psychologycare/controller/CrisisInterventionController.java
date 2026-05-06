package com.psychologycare.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.psychologycare.common.Result;
import com.psychologycare.entity.CrisisIntervention;
import com.psychologycare.service.CrisisInterventionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/intervention")
@RequiredArgsConstructor
public class CrisisInterventionController {
    private final CrisisInterventionService service;

    @GetMapping("/page")
    public Result<IPage<CrisisIntervention>> page(@RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody CrisisIntervention entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody CrisisIntervention entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/submit/{id}")
    public Result<Void> submit(@PathVariable Long id) {
        service.updateStatus(id, "SUBMITTED");
        return Result.success();
    }

    @PutMapping("/approve/{id}")
    public Result<Void> approve(@PathVariable Long id) {
        service.updateStatus(id, "APPROVED");
        return Result.success();
    }

}







