package com.citytoilet.controller;

import com.github.pagehelper.PageInfo;
import com.citytoilet.common.Result;
import com.citytoilet.entity.InspectionPlan;
import com.citytoilet.service.AuthService;
import com.citytoilet.service.InspectionPlanService;
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
@RequestMapping("/api/plan")
public class InspectionPlanController extends BaseController {
    private final InspectionPlanService service;
    private static final String[] WRITE_ROLES = {"ADMIN", "SANITATION", "INSPECTOR"};

    public InspectionPlanController(AuthService authService, InspectionPlanService service) {
        super(authService);
        this.service = service;
    }

    @GetMapping("/page")
    public Result<PageInfo<InspectionPlan>> page(@RequestAttribute("role") String role, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String keyword, @RequestParam(required = false) String status) {
        checkAuthenticated(role);
        return Result.success(service.page(pageNum, pageSize, keyword, status));
    }

    @PostMapping
    public Result<Void> add(@RequestAttribute("role") String role, @RequestBody InspectionPlan entity) {
        checkAnyRole(role, WRITE_ROLES);
        service.save(entity);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestAttribute("role") String role, @RequestBody InspectionPlan entity) {
        checkAnyRole(role, WRITE_ROLES);
        service.save(entity);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@RequestAttribute("role") String role, @PathVariable Long id) {
        checkAdmin(role);
        service.delete(id);
        return Result.success();
    }

    @PutMapping("/process/{id}")
    public Result<Void> process(@RequestAttribute("role") String role, @PathVariable Long id) {
        checkAnyRole(role, WRITE_ROLES);
        service.updateStatus(id, "DELIVERING");
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<Void> finish(@RequestAttribute("role") String role, @PathVariable Long id) {
        checkAnyRole(role, WRITE_ROLES);
        service.updateStatus(id, "CLOSED");
        return Result.success();
    }
}
