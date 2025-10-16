package com.xiaou.campusclub.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.campusclub.common.Result;
import com.xiaou.campusclub.dto.ClubRequest;
import com.xiaou.campusclub.service.ClubService;
import com.xiaou.campusclub.vo.ClubVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {
    
    @Autowired
    private ClubService clubService;
    
    @GetMapping
    public Result<IPage<ClubVO>> getClubList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword,
            HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(clubService.getClubList(page, size, category, keyword, userId));
    }
    
    @GetMapping("/{id}")
    public Result<ClubVO> getClubDetail(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(clubService.getClubDetail(id, userId));
    }
    
    @PostMapping
    public Result<Long> createClub(HttpServletRequest request, @RequestBody ClubRequest clubRequest) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(clubService.createClub(userId, clubRequest));
    }
    
    @PutMapping("/{id}")
    public Result<Void> updateClub(@PathVariable Long id, HttpServletRequest request, @RequestBody ClubRequest clubRequest) {
        Long userId = (Long) request.getAttribute("userId");
        clubService.updateClub(id, userId, clubRequest);
        return Result.success();
    }
    
    @PostMapping("/{id}/join")
    public Result<Void> joinClub(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        clubService.joinClub(id, userId);
        return Result.success();
    }
    
    @PutMapping("/{id}/members/{userId}/approve")
    public Result<Void> approveMember(@PathVariable Long id, @PathVariable Long userId, 
                                     @RequestParam Integer status, HttpServletRequest request) {
        Long currentUserId = (Long) request.getAttribute("userId");
        clubService.approveMember(id, currentUserId, userId, status);
        return Result.success();
    }
    
    @GetMapping("/my")
    public Result<List<ClubVO>> getMyClubs(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(clubService.getMyClubs(userId));
    }
    
    @PostMapping("/{id}/recruiting")
    public Result<Void> updateRecruitStatus(@PathVariable Long id, HttpServletRequest request,
                                           @RequestParam Integer isRecruiting,
                                           @RequestParam(required = false) String recruitInfo) {
        Long userId = (Long) request.getAttribute("userId");
        clubService.updateRecruitStatus(id, userId, isRecruiting, recruitInfo);
        return Result.success();
    }
}

