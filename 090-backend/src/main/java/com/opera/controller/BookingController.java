package com.opera.controller;

import com.github.pagehelper.PageInfo;
import com.opera.common.Result;
import com.opera.entity.BookingRecord;
import com.opera.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService selectionService;

    @GetMapping("/list")
    public Result<PageInfo<BookingRecord>> list(@RequestParam(defaultValue = "1") Integer pageNum,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @RequestParam(required = false) Long scheduleId,
                                                  @RequestParam(required = false) Long memberId,
                                                  @RequestParam(required = false) Integer selectStatus,
                                                  @RequestAttribute("role") String role,
                                                  @RequestAttribute("userId") Long userId) {
        return Result.success(selectionService.list(scheduleId, memberId, selectStatus, role, userId, pageNum, pageSize));
    }

    @PostMapping("/select")
    public Result<String> select(@RequestBody Map<String, Long> params, @RequestAttribute("userId") Long userId) {
        selectionService.selectCourse(params.get("scheduleId"), userId);
        return Result.success();
    }
}


