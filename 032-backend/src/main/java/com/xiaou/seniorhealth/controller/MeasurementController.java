package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.domain.Measurement;
import com.xiaou.seniorhealth.dto.MeasurementCreateDTO;
import com.xiaou.seniorhealth.repository.MeasurementRepository;
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
    public MeasurementController(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
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
    @GetMapping("/elder/{elderId}/latest")
    public ApiResponse<List<Measurement>> latest(@PathVariable Long elderId, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String type) {
        List<Measurement> list = type == null
                ? measurementRepository.findLatestByElder(elderId, size)
                : measurementRepository.findLatestByElderAndType(elderId, type, size);
        return ApiResponse.ok(list);
    }
}
