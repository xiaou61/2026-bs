package com.medicalwaste.controller;

import com.github.pagehelper.PageInfo;
import com.medicalwaste.common.Result;
import com.medicalwaste.entity.CollectionOrder;
import com.medicalwaste.service.AuthService;
import com.medicalwaste.service.CollectionOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class CollectionOrderController {
    private final AuthService authService;
    private final CollectionOrderService service;

    @GetMapping("/page")
    public Result<PageInfo<CollectionOrder>> page(@RequestAttribute("role") String role, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String keyword, @RequestParam(required = false) String status) {
        authService.assertAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody CollectionOrder entity) {
        authService.assertAnyRole(role, "ADMIN", "HOSPITAL", "COLLECTOR", "TRANSPORTER");
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody CollectionOrder entity) {
        authService.assertAnyRole(role, "ADMIN", "HOSPITAL", "COLLECTOR", "TRANSPORTER");
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/process/{id}")
    public Result<Void> process(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "HOSPITAL", "COLLECTOR", "TRANSPORTER");
        service.updateStatus(id, "PROCESSING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute("role") String role, @PathVariable Long id) {
        authService.assertAnyRole(role, "ADMIN", "HOSPITAL", "COLLECTOR", "TRANSPORTER");
        service.updateStatus(id, "FINISHED");
        return Result.success();
    }
}
