package com.property.controller;

import com.property.common.BusinessException;
import com.property.common.Result;
import com.property.entity.Complaint;
import com.property.service.ComplaintService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/complaint")
public class ComplaintController {

    @Resource
    private ComplaintService complaintService;

    @GetMapping("/my-page")
    public Result<?> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            @RequestParam(required = false) Integer status,
                            @RequestParam(required = false) String orderNo,
                            HttpServletRequest request) {
        checkOwner((String) request.getAttribute("role"));
        return Result.success(complaintService.myPage((Long) request.getAttribute("userId"), pageNum, pageSize, status, orderNo));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Long ownerId,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String orderNo,
                          HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        return Result.success(complaintService.page(pageNum, pageSize, ownerId, status, orderNo));
    }

    @PostMapping
    public Result<?> add(@RequestBody Complaint complaint, HttpServletRequest request) {
        checkOwner((String) request.getAttribute("role"));
        complaintService.save(complaint, (Long) request.getAttribute("userId"));
        return Result.success();
    }

    @PutMapping("/reply")
    public Result<?> reply(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkStaffRole((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("reply") == null) {
            throw new BusinessException("参数不完整");
        }
        complaintService.reply(((Number) params.get("id")).longValue(), params.get("reply").toString());
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id, HttpServletRequest request) {
        complaintService.deleteById(id, (Long) request.getAttribute("userId"), (String) request.getAttribute("role"));
        return Result.success();
    }

    private void checkStaffRole(String role) {
        if (!"ADMIN".equals(role) && !"STAFF".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }

    private void checkOwner(String role) {
        if (!"OWNER".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
