package com.hospital.controller;

import com.github.pagehelper.PageInfo;
import com.hospital.common.Result;
import com.hospital.entity.DoctorInfo;
import com.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/page")
    public Result<PageInfo<DoctorInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                             @RequestParam(required = false) Long departmentId,
                                             @RequestParam(required = false) String keyword,
                                             @RequestParam(required = false) Integer status,
                                             @RequestAttribute("role") String role) {
        return Result.success(doctorService.page(departmentId, keyword, status, pageNum, pageSize, role));
    }

    @GetMapping("/public/list")
    public Result<List<DoctorInfo>> publicList(@RequestParam(required = false) Long departmentId,
                                               @RequestParam(required = false) String keyword) {
        return Result.success(doctorService.publicList(departmentId, keyword));
    }

    @GetMapping("/enabled")
    public Result<List<DoctorInfo>> enabled() {
        return Result.success(doctorService.enabledList());
    }

    @GetMapping("/my")
    public Result<DoctorInfo> myInfo(@RequestAttribute("userId") Long userId) {
        return Result.success(doctorService.getByUserId(userId));
    }

    @PostMapping
    public Result<String> add(@RequestBody DoctorInfo entity,
                              @RequestAttribute("userId") Long userId,
                              @RequestAttribute("role") String role) {
        doctorService.save(entity, userId, role);
        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody DoctorInfo entity,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        doctorService.save(entity, userId, role);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        doctorService.delete(id, userId, role);
        return Result.success();
    }
}
