package com.hospital.controller;

import com.github.pagehelper.PageInfo;
import com.hospital.common.Result;
import com.hospital.entity.AppointmentRecord;
import com.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/page")
    public Result<PageInfo<AppointmentRecord>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                                    @RequestParam(required = false) String keyword,
                                                    @RequestParam(required = false) Integer status,
                                                    @RequestParam(required = false) Long doctorId,
                                                    @RequestParam(required = false) Long departmentId,
                                                    @RequestAttribute("role") String role) {
        return Result.success(appointmentService.page(keyword, status, doctorId, departmentId, pageNum, pageSize, role));
    }

    @GetMapping("/my")
    public Result<PageInfo<AppointmentRecord>> myPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize,
                                                      @RequestParam(required = false) Integer status,
                                                      @RequestAttribute("userId") Long userId) {
        return Result.success(appointmentService.myPage(userId, status, pageNum, pageSize));
    }

    @GetMapping("/doctor")
    public Result<PageInfo<AppointmentRecord>> doctorPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                                          @RequestParam(required = false) Integer status,
                                                          @RequestAttribute("userId") Long userId) {
        return Result.success(appointmentService.doctorPage(userId, status, pageNum, pageSize));
    }

    @PostMapping("/create")
    public Result<Map<String, Object>> create(@RequestBody Map<String, Object> params,
                                              @RequestAttribute("userId") Long userId,
                                              @RequestAttribute("role") String role) {
        Long scheduleId = params.get("scheduleId") == null ? null : Long.valueOf(String.valueOf(params.get("scheduleId")));
        Long cardId = params.get("cardId") == null ? null : Long.valueOf(String.valueOf(params.get("cardId")));
        String remark = params.get("remark") == null ? null : String.valueOf(params.get("remark"));
        return Result.success(appointmentService.create(scheduleId, cardId, remark, userId, role));
    }

    @PutMapping("/cancel/{id}")
    public Result<String> cancel(@PathVariable Long id,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        appointmentService.cancel(id, userId, role);
        return Result.success();
    }

    @PutMapping("/finish/{id}")
    public Result<String> finish(@PathVariable Long id,
                                 @RequestAttribute("userId") Long userId,
                                 @RequestAttribute("role") String role) {
        appointmentService.finish(id, userId, role);
        return Result.success();
    }
}
