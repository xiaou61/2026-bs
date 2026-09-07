package com.xiaou.dreamdonation.controller;

import com.xiaou.dreamdonation.common.Result;
import com.xiaou.dreamdonation.dto.DonationCreateDTO;
import com.xiaou.dreamdonation.entity.Donation;
import com.xiaou.dreamdonation.entity.User;
import com.xiaou.dreamdonation.service.DonationService;
import com.xiaou.dreamdonation.service.UserService;
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
    private final UserService userService;

    @PostMapping
    public Result<?> createDonation(@Valid @RequestBody DonationCreateDTO dto,
                                     @RequestHeader(value = "Authorization", required = false) String token) {
        User user = userService.getAuthenticatedUser(token);
        return Result.success(donationService.createDonation(dto, user.getId()));
    }

    @GetMapping("/my")
    public Result<Page<Donation>> getMyDonations(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        User user = userService.getAuthenticatedUser(token);
        return Result.success(donationService.getMyDonations(user.getId(), page, size));
    }

    @GetMapping("/project/{projectId}")
    public Result<Page<Donation>> getProjectDonations(
            @PathVariable Long projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return Result.success(donationService.getProjectDonations(projectId, page, size));
    }
}
