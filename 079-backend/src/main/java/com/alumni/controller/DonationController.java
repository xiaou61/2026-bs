package com.alumni.controller;

import com.alumni.common.Result;
import com.alumni.entity.DonationProject;
import com.alumni.entity.DonationRecord;
import com.alumni.service.DonationService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @GetMapping("/project/list")
    public Result<Page<DonationProject>> projectList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                                     Integer status) {
        return Result.success(donationService.projectList(pageNum, pageSize, status));
    }

    @GetMapping("/project/{id}")
    public Result<DonationProject> getProjectById(@PathVariable Long id) {
        return Result.success(donationService.getProjectById(id));
    }

    @PostMapping("/project")
    public Result<?> addProject(@RequestBody DonationProject project) {
        donationService.addProject(project);
        return Result.success();
    }

    @PutMapping("/project")
    public Result<?> updateProject(@RequestBody DonationProject project) {
        donationService.updateProject(project);
        return Result.success();
    }

    @DeleteMapping("/project/{id}")
    public Result<?> deleteProject(@PathVariable Long id) {
        donationService.deleteProject(id);
        return Result.success();
    }

    @PostMapping("/donate")
    public Result<?> donate(@RequestBody DonationRecord record, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        record.setUserId(userId);
        donationService.donate(record);
        return Result.success();
    }

    @GetMapping("/record/list")
    public Result<Page<DonationRecord>> recordList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                                   Long projectId, Long userId) {
        return Result.success(donationService.recordList(pageNum, pageSize, projectId, userId));
    }

    @GetMapping("/record/my")
    public Result<Page<DonationRecord>> myRecords(@RequestParam(defaultValue = "1") Integer pageNum,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(donationService.recordList(pageNum, pageSize, null, userId));
    }

    @PutMapping("/record/{id}/confirm")
    public Result<?> confirmRecord(@PathVariable Long id) {
        donationService.confirmRecord(id);
        return Result.success();
    }
}
