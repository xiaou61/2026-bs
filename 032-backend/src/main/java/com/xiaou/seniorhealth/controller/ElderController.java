package com.xiaou.seniorhealth.controller;

import com.xiaou.seniorhealth.common.ApiResponse;
import com.xiaou.seniorhealth.domain.Elder;
import com.xiaou.seniorhealth.dto.ElderCreateDTO;
import com.xiaou.seniorhealth.repository.ElderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/elders")
public class ElderController {
    private final ElderRepository elderRepository;
    public ElderController(ElderRepository elderRepository) {
        this.elderRepository = elderRepository;
    }
    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    @PostMapping
    public ApiResponse<Elder> create(@Valid @RequestBody ElderCreateDTO dto) {
        Elder elder = new Elder();
        BeanUtils.copyProperties(dto, elder);
        elder.setCreatedAt(LocalDateTime.now());
        Elder saved = elderRepository.save(elder);
        return ApiResponse.ok(saved);
    }
    @GetMapping("/{id}")
    public ApiResponse<Elder> detail(@PathVariable Long id) {
        Elder e = elderRepository.findById(id).orElse(null);
        return ApiResponse.ok(e);
    }
    @GetMapping
    public ApiResponse<List<Elder>> list() {
        Iterable<Elder> all = elderRepository.findAll();
        List<Elder> list = new ArrayList<>();
        all.forEach(list::add);
        return ApiResponse.ok(list);
    }
}
