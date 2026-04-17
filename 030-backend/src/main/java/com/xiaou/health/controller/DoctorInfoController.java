package com.xiaou.health.controller;

import com.xiaou.health.common.Constants;
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
            if (!UserContext.hasRole(Constants.ROLE_DOCTOR)) {
                return Result.error(403, "仅医生可维护医生资料");
            }
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
            if (!UserContext.hasRole(Constants.ROLE_DOCTOR)) {
                return Result.error(403, "仅医生可查看医生资料");
            }
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
            if (!UserContext.hasRole(Constants.ROLE_ADMIN)) {
                return Result.error(403, "仅管理员可查看待审核医生");
            }
            List<DoctorInfo> list = doctorInfoService.getPendingDoctors();
            return Result.success(list);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/{id}/verify")
    public Result<DoctorInfo> verifyDoctor(@PathVariable Long id, @RequestParam Integer status) {
        try {
            if (!UserContext.hasRole(Constants.ROLE_ADMIN)) {
                return Result.error(403, "仅管理员可审核医生");
            }
            DoctorInfo doctorInfo = doctorInfoService.verifyDoctor(id, status);
            return Result.success(doctorInfo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
