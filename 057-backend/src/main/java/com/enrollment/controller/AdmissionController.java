package com.enrollment.controller;

import com.enrollment.common.Result;
import com.enrollment.entity.Admission;
import com.enrollment.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admission")
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    @GetMapping("/page")
    public Result<?> getPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam(required = false) String studentName,
                             @RequestParam(required = false) Integer status) {
        return Result.success(admissionService.getPage(pageNum, pageSize, studentName, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody Admission admission) {
        admissionService.add(admission);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Admission admission) {
        admissionService.update(admission);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        admissionService.delete(id);
        return Result.success();
    }

    @PutMapping("/confirm/{id}")
    public Result<?> confirm(@PathVariable Long id) {
        admissionService.confirm(id);
        return Result.success();
    }

    @PostMapping("/batch")
    public Result<?> batchAdmit(@RequestBody List<Admission> admissions) {
        admissionService.batchAdmit(admissions);
        return Result.success();
    }
}
