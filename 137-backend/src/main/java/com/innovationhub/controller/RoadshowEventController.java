package com.innovationhub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.innovationhub.common.Result;
import com.innovationhub.entity.RoadshowEvent;
import com.innovationhub.service.AuthService;
import com.innovationhub.service.RoadshowEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roadshow")
@RequiredArgsConstructor
public class RoadshowEventController {
    private final AuthService authService;
    private final RoadshowEventService service;

    @GetMapping("/page")
    public Result<IPage<RoadshowEvent>> page(@RequestAttribute String role,
                                             @RequestParam(required = false) Integer pageNum,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute String role, @RequestBody RoadshowEvent entity) {
        authService.assertAdminOrMentor(role);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute String role, @RequestBody RoadshowEvent entity) {
        authService.assertAdminOrMentor(role);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/process/{id}")
    public Result<Void> process(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrMentor(role);
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute String role, @PathVariable Long id) {
        authService.assertAdminOrMentor(role);
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }

}


