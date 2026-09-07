package com.xiaou.recruitment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaou.recruitment.common.Result;
import com.xiaou.recruitment.entity.Company;
import com.xiaou.recruitment.service.ApplicationService;
import com.xiaou.recruitment.service.CompanyService;
import com.xiaou.recruitment.service.InterviewService;
import com.xiaou.recruitment.service.JobService;
import com.xiaou.recruitment.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobService jobService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private InterviewService interviewService;

    @GetMapping("/overview")
    public Result<?> getOverview(HttpServletRequest request) {
        if (!isAdmin(request)) {
            return Result.error(403, "仅管理员可以查看后台数据");
        }
        Map<String, Object> data = new HashMap<>();
        data.put("userCount", userService.count());
        data.put("companyCount", companyService.count());
        data.put("pendingCompanyCount", companyService.count(
                new LambdaQueryWrapper<Company>().eq(Company::getStatus, 0)));
        data.put("jobCount", jobService.count());
        data.put("applicationCount", applicationService.count());
        data.put("interviewCount", interviewService.count());
        return Result.success(data);
    }

    @GetMapping("/company/list")
    public Result<?> getCompanyList(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Integer status,
            HttpServletRequest request) {
        if (!isAdmin(request)) {
            return Result.error(403, "仅管理员可以查看企业列表");
        }
        IPage<Company> companyPage = companyService.getCompanyList(page, size, status);
        return Result.success(companyPage);
    }

    @PutMapping("/company/review")
    public Result<?> reviewCompany(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return Result.error(403, "仅管理员可以审核企业");
        }
        Long id = Long.parseLong(params.get("id").toString());
        Integer status = Integer.parseInt(params.get("status").toString());
        if (companyService.reviewCompany(id, status)) {
            return Result.success();
        }
        return Result.error("审核失败");
    }

    private boolean isAdmin(HttpServletRequest request) {
        return "admin".equals(request.getAttribute("role"));
    }
}
