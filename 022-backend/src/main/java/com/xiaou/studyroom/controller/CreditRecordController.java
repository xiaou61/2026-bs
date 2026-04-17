package com.xiaou.studyroom.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.studyroom.common.Result;
import com.xiaou.studyroom.entity.CreditRecord;
import com.xiaou.studyroom.service.CreditRecordService;
import com.xiaou.studyroom.utils.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/credit")
@CrossOrigin
public class CreditRecordController {

    @Autowired
    private CreditRecordService creditRecordService;

    @Autowired
    private AuthHelper authHelper;

    @GetMapping("/user/{userId}")
    public Result<Page<CreditRecord>> getUserCreditRecords(@PathVariable Long userId,
                                                         @RequestParam(defaultValue = "1") int current,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestHeader("Authorization") String token) {
        authHelper.requireAdmin(token);
        Page<CreditRecord> page = creditRecordService.getUserCreditRecords(userId, current, size);
        return Result.success(page);
    }

    @GetMapping("/my")
    public Result<Page<CreditRecord>> getMyCreditRecords(@RequestHeader("Authorization") String token,
                                                        @RequestParam(defaultValue = "1") int current,
                                                        @RequestParam(defaultValue = "10") int size) {
        Long userId = authHelper.getUserId(token);
        Page<CreditRecord> page = creditRecordService.getUserCreditRecords(userId, current, size);
        return Result.success(page);
    }

    @GetMapping("/recent/{userId}")
    public Result<List<CreditRecord>> getRecentCreditRecords(@PathVariable Long userId,
                                                           @RequestParam(defaultValue = "10") int limit,
                                                           @RequestHeader("Authorization") String token) {
        authHelper.requireAdmin(token);
        List<CreditRecord> records = creditRecordService.getRecentCreditRecords(userId, limit);
        return Result.success(records);
    }

    @GetMapping("/page")
    public Result<Page<CreditRecord>> getCreditRecordPage(@RequestHeader("Authorization") String token,
                                                         @RequestParam(defaultValue = "1") int current,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(required = false) Long userId,
                                                         @RequestParam(required = false) String reason) {
        authHelper.requireAdmin(token);
        Page<CreditRecord> page = creditRecordService.getCreditRecordPage(current, size, userId, reason);
        return Result.success(page);
    }

    @PostMapping("/record")
    public Result<String> addCreditRecord(@RequestHeader("Authorization") String token,
                                          @RequestBody Map<String, Object> requestMap) {
        authHelper.requireAdmin(token);
        try {
            Long userId = Long.valueOf(requestMap.get("userId").toString());
            Integer scoreChange = Integer.valueOf(requestMap.get("scoreChange").toString());
            String changeReason = requestMap.get("changeReason").toString();
            String relatedType = requestMap.getOrDefault("relatedType", "ADMIN").toString();
            Long relatedId = requestMap.get("relatedId") != null ?
                Long.valueOf(requestMap.get("relatedId").toString()) : null;

            if (creditRecordService.addCreditRecord(userId, scoreChange, changeReason, relatedType, relatedId)) {
                return Result.success("信用记录添加成功");
            }
            return Result.error("信用记录添加失败");
        } catch (Exception e) {
            return Result.error("添加失败：" + e.getMessage());
        }
    }

    @GetMapping("/statistics/{userId}")
    public Result<Map<String, Object>> getCreditStatistics(@PathVariable Long userId,
                                                          @RequestHeader("Authorization") String token,
                                                          @RequestParam(required = false) String startDate,
                                                          @RequestParam(required = false) String endDate) {
        Long currentUserId = authHelper.getUserId(token);
        if (!currentUserId.equals(userId) && !authHelper.isAdmin(token)) {
            return Result.error("无权访问该用户统计");
        }
        try {
            LocalDateTime start = startDate != null ?
                LocalDateTime.parse(startDate + "T00:00:00") :
                LocalDateTime.now().minusDays(30);
            LocalDateTime end = endDate != null ?
                LocalDateTime.parse(endDate + "T23:59:59") :
                LocalDateTime.now();

            int totalChange = creditRecordService.getTotalCreditChange(userId, start, end);

            Map<String, Object> statistics = Map.of(
                "totalChange", totalChange,
                "startDate", start,
                "endDate", end,
                "userId", userId
            );

            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取统计信息失败：" + e.getMessage());
        }
    }
}
