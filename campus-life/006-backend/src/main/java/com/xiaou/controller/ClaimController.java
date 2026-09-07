package com.xiaou.controller;

import com.xiaou.common.Result;
import com.xiaou.entity.Claim;
import com.xiaou.entity.User;
import com.xiaou.service.ClaimService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/claim")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping
    public Result<?> submitClaim(@RequestBody Claim claim, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");
            claim.setClaimerId(user.getId());
            claimService.submitClaim(claim);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/sent")
    public Result<List<Claim>> getSentClaims(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Claim> claims = claimService.getUserSentClaims(user.getId());
        return Result.success(claims);
    }

    @GetMapping("/received")
    public Result<List<Claim>> getReceivedClaims(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Claim> claims = claimService.getUserReceivedClaims(user.getId());
        return Result.success(claims);
    }

    @PutMapping("/{id}/review")
    public Result<?> reviewClaim(@PathVariable Long id, 
                                  @RequestParam Integer status, 
                                  @RequestParam(required = false) String reply) {
        try {
            claimService.reviewClaim(id, status, reply);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<?> deleteClaim(@PathVariable Long id) {
        try {
            claimService.deleteClaim(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

