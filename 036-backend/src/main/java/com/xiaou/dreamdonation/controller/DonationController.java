package com.xiaou.dreamdonation.controller;

import com.xiaou.dreamdonation.common.Result;
import com.xiaou.dreamdonation.dto.DonationCreateDTO;
import com.xiaou.dreamdonation.entity.Donation;
import com.xiaou.dreamdonation.service.DonationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
@CrossOrigin
public class DonationController {
    private final DonationService donationService;

    @PostMapping
    public Result&lt;?&gt; createDonation(@Valid @RequestBody DonationCreateDTO dto,
                                     @RequestHeader(value = "userId", defaultValue = "1") Long userId) {
        try {
            return Result.success(donationService.createDonation(dto, userId));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/my")
    public Result&lt;Page&lt;Donation&gt;&gt; getMyDonations(
            @RequestHeader(value = "userId", defaultValue = "1") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return Result.success(donationService.getMyDonations(userId, page, size));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/project/{projectId}")
    public Result&lt;Page&lt;Donation&gt;&gt; getProjectDonations(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            return Result.success(donationService.getProjectDonations(projectId, page, size));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
