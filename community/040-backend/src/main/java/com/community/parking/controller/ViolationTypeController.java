package com.community.parking.controller;

import com.community.parking.common.Result;
import com.community.parking.entity.ViolationType;
import com.community.parking.repository.ViolationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/violation-types")
@RequiredArgsConstructor
@CrossOrigin
public class ViolationTypeController {
    private final ViolationTypeRepository violationTypeRepository;

    @GetMapping
    public Result<List<ViolationType>> getAllViolationTypes() {
        try {
            List<ViolationType> types = violationTypeRepository.findAll();
            return Result.success(types);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
