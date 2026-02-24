package com.bike.controller;

import com.bike.common.Result;
import com.bike.service.CreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/credit")
public class CreditController {

    @Autowired
    private CreditService creditService;

    @GetMapping("/score")
    public Result<?> getScore(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(creditService.getScore(userId));
    }

    @GetMapping("/records")
    public Result<?> getRecords(HttpServletRequest request,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) request.getAttribute("userId");
        return Result.success(creditService.getRecords(userId, pageNum, pageSize));
    }

    @PostMapping("/adjust")
    public Result<?> adjust(@RequestBody Map<String, Object> params) {
        Long userId = Long.parseLong(params.get("userId").toString());
        Integer scoreChange = (Integer) params.get("scoreChange");
        String description = (String) params.get("description");
        creditService.adjust(userId, scoreChange, description);
        return Result.success();
    }
}
