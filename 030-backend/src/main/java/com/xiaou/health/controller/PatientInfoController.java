package com.xiaou.health.controller;

import com.xiaou.health.common.Constants;
import com.xiaou.health.common.Result;
import com.xiaou.health.entity.PatientInfo;
import com.xiaou.health.service.PatientInfoService;
import com.xiaou.health.util.UserContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
public class PatientInfoController {
    private final PatientInfoService patientInfoService;

    public PatientInfoController(PatientInfoService patientInfoService) {
        this.patientInfoService = patientInfoService;
    }

    @PostMapping("/info")
    public Result<PatientInfo> createOrUpdatePatientInfo(@RequestBody PatientInfo patientInfo) {
        try {
            if (!UserContext.hasRole(Constants.ROLE_PATIENT)) {
                return Result.error(403, "仅患者可维护健康档案");
            }
            Long userId = UserContext.getUserId();
            PatientInfo result = patientInfoService.createOrUpdatePatientInfo(userId, patientInfo);
            return Result.success(result);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/info")
    public Result<PatientInfo> getPatientInfo() {
        try {
            if (!UserContext.hasRole(Constants.ROLE_PATIENT)) {
                return Result.error(403, "仅患者可查看健康档案");
            }
            Long userId = UserContext.getUserId();
            PatientInfo patientInfo = patientInfoService.getPatientInfo(userId);
            return Result.success(patientInfo);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
