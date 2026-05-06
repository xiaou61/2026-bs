package com.learningpath.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.learningpath.common.Result;
import com.learningpath.entity.CompetencyProfile;
import com.learningpath.service.CompetencyProfileService;
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
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class CompetencyProfileController {
    private final CompetencyProfileService service;

    @GetMapping("/page")
    public Result<IPage<CompetencyProfile>> page(@RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize,
                                    @RequestParam(required = false) String keyword,
                                    @RequestParam(required = false) String status) {
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestBody CompetencyProfile entity) {
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody CompetencyProfile entity) {
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/process/{id}")
    public Result<Void> process(@PathVariable Long id) {
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@PathVariable Long id) {
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }

}



