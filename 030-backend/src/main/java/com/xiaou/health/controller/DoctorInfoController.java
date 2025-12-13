package com.xiaou.health.controller;

import com.xiaou.health.common.Result;
import com.xiaou.health.entity.DoctorInfo;
import com.xiaou.health.service.DoctorInfoService;
import com.xiaou.health.util.UserContext;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorInfoController {
    private final DoctorInfoService doctorInfoService;

    public DoctorInfoController(DoctorInfoService doctorInfoService) {
        this.doctorInfoService = doctorInfoService;
    }

    @PostMapping("/info")
    public Result<DoctorInfo> createOrUpdateDoctorInfo(@RequestBody DoctorInfo doctorInfo) {
        try {
            Long userId = UserContext.getUserId();
            DoctorInfo result = doctorInfoService.createOrUpdateDoctorInfo(userId, doctorInfo);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result<DoctorInfo> getDoctorInfo() {
        try {
            Long userId = UserContext.getUserId();
            DoctorInfo doctorInfo = doctorInfoService.getDoctorInfo(userId);
            return Result.success(doctorInfo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/verified")
    public Result<List<DoctorInfo>> getVerifiedDoctors() {
        try {
            List<DoctorInfo> list = doctorInfoService.getVerifiedDoctors();
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/pending")
    public Result<List<DoctorInfo>> getPendingDoctors() {
        try {
            List<DoctorInfo> list = doctorInfoService.getPendingDoctors();
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{id}/verify")
    public Result<DoctorInfo> verifyDoctor(@PathVariable Long id, @RequestParam Integer status) {
        try {
            DoctorInfo doctorInfo = doctorInfoService.verifyDoctor(id, status);
            return Result.success(doctorInfo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
