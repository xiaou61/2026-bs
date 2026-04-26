package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.domain.Elder;
import com.xiaou.seniorhealth.domain.Measurement;
import com.xiaou.seniorhealth.repository.MeasurementRepository;
import com.xiaou.seniorhealth.security.CurrentUserSupport;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/assessment")
public class HealthAssessmentController {
    private final MeasurementRepository measurementRepository;
    private final CurrentUserSupport currentUserSupport;

    public HealthAssessmentController(MeasurementRepository measurementRepository, CurrentUserSupport currentUserSupport) {
        this.measurementRepository = measurementRepository;
        this.currentUserSupport = currentUserSupport;
    }

    @PreAuthorize("hasRole('ELDER')")
    @GetMapping("/me")
    public ApiResponse<Map<String, Object>> myAssessment() {
        Elder elder = currentUserSupport.getCurrentElderProfile();
        if (elder == null) {
            return ApiResponse.fail("elder profile not found");
        }
        return assessInternal(elder.getId());
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/elder/{elderId}")
    public ApiResponse<Map<String, Object>> assess(@PathVariable Long elderId) {
        return assessInternal(elderId);
    }

    private ApiResponse<Map<String, Object>> assessInternal(Long elderId) {
        Map<String, Object> r = new HashMap<>();
        List<Measurement> w = measurementRepository.findLatestByElderAndType(elderId, "WEIGHT", 1);
        List<Measurement> h = measurementRepository.findLatestByElderAndType(elderId, "HEIGHT", 1);
        if (!w.isEmpty() && !h.isEmpty() && w.get(0).getValue1() != null && h.get(0).getValue1() != null) {
            double weight = w.get(0).getValue1();
            double height = h.get(0).getValue1();
            double bmi = weight / Math.pow(height / 100.0, 2);
            r.put("bmi", Math.round(bmi * 10.0) / 10.0);
        }
        List<Measurement> bp = measurementRepository.findLatestByElderAndType(elderId, "BP", 1);
        if (!bp.isEmpty() && bp.get(0).getValue1() != null && bp.get(0).getValue2() != null) {
            double sbp = bp.get(0).getValue1();
            double dbp = bp.get(0).getValue2();
            String status = (sbp >= 140 || dbp >= 90) ? "HIGH" : (sbp >= 120 || dbp >= 80) ? "ELEVATED" : "NORMAL";
            r.put("bpStatus", status);
        }
        String risk = "LOW";
        Object bmiObj = r.get("bmi");
        Object bpObj = r.get("bpStatus");
        if ((bmiObj instanceof Number && ((Number) bmiObj).doubleValue() >= 28) || "HIGH".equals(bpObj)) risk = "HIGH";
        else if ((bmiObj instanceof Number && ((Number) bmiObj).doubleValue() >= 24) || "ELEVATED".equals(bpObj)) risk = "MEDIUM";
        r.put("risk", risk);
        return ApiResponse.ok(r);
    }
}
