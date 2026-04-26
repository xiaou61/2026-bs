package com.hospital.controller;

import com.github.pagehelper.PageInfo;
import com.hospital.common.Result;
import com.hospital.entity.DoctorReview;
import com.hospital.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/page")
    public Result<PageInfo<DoctorReview>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(required = false) Long doctorId,
                                               @RequestParam(required = false) String keyword,
                                               @RequestAttribute("role") String role) {
        return Result.success(reviewService.page(doctorId, keyword, pageNum, pageSize, role));
    }

    @GetMapping("/doctor/{doctorId}")
    public Result<List<DoctorReview>> byDoctorId(@PathVariable Long doctorId) {
        return Result.success(reviewService.byDoctorId(doctorId));
    }

    @PostMapping("/save")
    public Result<String> save(@RequestBody DoctorReview entity,
                               @RequestAttribute("userId") Long userId,
                               @RequestAttribute("role") String role) {
        reviewService.save(entity, userId, role);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Long id,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        reviewService.delete(id, userId, role);
        return Result.success();
    }
}
