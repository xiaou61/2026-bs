package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.domain.Elder;
import com.xiaou.seniorhealth.domain.Measurement;
import com.xiaou.seniorhealth.dto.MeasurementCreateDTO;
import com.xiaou.seniorhealth.repository.MeasurementRepository;
import com.xiaou.seniorhealth.security.CurrentUserSupport;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/measurements")
public class MeasurementController {
    private final MeasurementRepository measurementRepository;
    private final CurrentUserSupport currentUserSupport;

    public MeasurementController(MeasurementRepository measurementRepository, CurrentUserSupport currentUserSupport) {
        this.measurementRepository = measurementRepository;
        this.currentUserSupport = currentUserSupport;
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PostMapping
    public ApiResponse<Measurement> create(@Valid @RequestBody MeasurementCreateDTO dto) {
        Measurement m = new Measurement();
        BeanUtils.copyProperties(dto, m);
        m.setCreatedAt(LocalDateTime.now());
        Measurement saved = measurementRepository.save(m);
        return ApiResponse.ok(saved);
    }

    @PreAuthorize("hasRole('ELDER')")
    @GetMapping("/me/latest")
    public ApiResponse<List<Measurement>> myLatest(@RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String type) {
        Elder elder = currentUserSupport.getCurrentElderProfile();
        if (elder == null) {
            return ApiResponse.fail("elder profile not found");
        }
        List<Measurement> list = type == null
                ? measurementRepository.findLatestByElder(elder.getId(), size)
                : measurementRepository.findLatestByElderAndType(elder.getId(), type, size);
        return ApiResponse.ok(list);
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @GetMapping("/elder/{elderId}/latest")
    public ApiResponse<List<Measurement>> latest(@PathVariable Long elderId, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String type) {
        List<Measurement> list = type == null
                ? measurementRepository.findLatestByElder(elderId, size)
                : measurementRepository.findLatestByElderAndType(elderId, type, size);
        return ApiResponse.ok(list);
    }
}
