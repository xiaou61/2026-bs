package com.game.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.game.common.BusinessException;
import com.game.common.Result;
import com.game.entity.Complaint;
import com.game.service.ComplaintService;
import com.game.vo.ComplaintVO;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) Integer status,
                          @RequestParam(required = false) String orderNo,
                          HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        Page<ComplaintVO> page = complaintService.page(pageNum, pageSize, status, orderNo);
        return Result.success(page);
    }

    @GetMapping("/my")
    public Result<?> my(@RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(required = false) Integer status,
                        @RequestParam(required = false) String orderNo,
                        HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        Page<ComplaintVO> page = complaintService.myPage(userId, pageNum, pageSize, status, orderNo);
        return Result.success(page);
    }

    @PostMapping
    public Result<?> add(@RequestBody Complaint complaint, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        complaintService.add(complaint, userId);
        return Result.success();
    }

    @PutMapping("/reply")
    public Result<?> reply(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        checkAdmin((String) request.getAttribute("role"));
        if (params.get("id") == null || params.get("reply") == null) {
            throw new BusinessException("参数不完整");
        }
        Long id = ((Number) params.get("id")).longValue();
        String reply = params.get("reply").toString();
        Long adminId = (Long) request.getAttribute("userId");
        complaintService.reply(id, reply, adminId);
        return Result.success();
    }

    private void checkAdmin(String role) {
        if (!"ADMIN".equals(role)) {
            throw new BusinessException("无权限操作");
        }
    }
}
