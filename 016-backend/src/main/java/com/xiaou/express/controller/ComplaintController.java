package com.xiaou.express.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.express.common.Result;
import com.xiaou.express.dto.ComplaintRequest;
import com.xiaou.express.service.ComplaintService;
import com.xiaou.express.util.UserContext;
import com.xiaou.express.vo.ComplaintVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping
    public Result<Void> createComplaint(@RequestBody ComplaintRequest request) {
        Long userId = UserContext.getCurrentUserId();
        complaintService.createComplaint(userId, request);
        return Result.success();
    }

    @GetMapping("/my")
    public Result<Page<ComplaintVO>> getMyComplaints(@RequestParam(defaultValue = "1") int pageNum,
                                                     @RequestParam(defaultValue = "10") int pageSize) {
        Long userId = UserContext.getCurrentUserId();
        return Result.success(complaintService.getMyComplaints(userId, pageNum, pageSize));
    }
}

