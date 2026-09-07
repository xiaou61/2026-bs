package com.milk.controller;

import com.milk.common.Result;
import com.milk.entity.Complaint;
import com.milk.service.ComplaintService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/complaint")
public class ComplaintController {

    @Resource
    private ComplaintService complaintService;

    @GetMapping("/my")
    public Result<?> my(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(complaintService.listByUserId(userId));
    }

    @GetMapping("/page")
    public Result<?> page(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "10") Integer pageSize,
                          @RequestParam(required = false) String type,
                          @RequestParam(required = false) Integer status) {
        return Result.success(complaintService.page(pageNum, pageSize, type, status));
    }

    @PostMapping
    public Result<?> add(@RequestBody Complaint complaint, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        complaint.setUserId(userId);
        complaintService.save(complaint);
        return Result.success();
    }

    @PutMapping("/reply")
    public Result<?> reply(@RequestBody Map<String, Object> params) {
        Long id = Long.valueOf(params.get("id").toString());
        String reply = params.get("reply").toString();
        complaintService.reply(id, reply);
        return Result.success();
    }
}
