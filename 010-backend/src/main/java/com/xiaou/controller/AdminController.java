package com.xiaou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.common.Result;
import com.xiaou.entity.*;
import com.xiaou.mapper.UserMapper;
import com.xiaou.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private SeatService seatService;
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private ViolationService violationService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private SystemConfigService systemConfigService;
    
    @Autowired
    private TimeSlotService timeSlotService;

    @GetMapping("/user/list")
    public Result<List<User>> listUsers() {
        List<User> users = userMapper.selectList(null);
        users.forEach(user -> user.setPassword(null));
        return Result.success(users);
    }

    @PutMapping("/user/{id}/status")
    public Result<String> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        User user = userMapper.selectById(id);
        user.setStatus(params.get("status"));
        userMapper.updateById(user);
        return Result.success("操作成功");
    }

    @PutMapping("/user/{id}/credit")
    public Result<String> resetCredit(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        User user = userMapper.selectById(id);
        user.setCreditScore(params.get("creditScore"));
        userMapper.updateById(user);
        return Result.success("操作成功");
    }

    @GetMapping("/floor/list")
    public Result<List<Floor>> listFloors() {
        return Result.success(seatService.listFloors());
    }

    @PostMapping("/floor")
    public Result<Floor> saveFloor(@RequestBody Floor floor) {
        return Result.success(seatService.saveFloor(floor));
    }

    @DeleteMapping("/floor/{id}")
    public Result<String> deleteFloor(@PathVariable Long id) {
        seatService.deleteFloor(id);
        return Result.success("删除成功");
    }

    @GetMapping("/area/list")
    public Result<List<Area>> listAreas(@RequestParam(required = false) Long floorId) {
        return Result.success(seatService.listAreas(floorId));
    }

    @PostMapping("/area")
    public Result<Area> saveArea(@RequestBody Area area) {
        return Result.success(seatService.saveArea(area));
    }

    @DeleteMapping("/area/{id}")
    public Result<String> deleteArea(@PathVariable Long id) {
        seatService.deleteArea(id);
        return Result.success("删除成功");
    }

    @GetMapping("/seat/list")
    public Result<List<Seat>> listSeats(@RequestParam(required = false) Long areaId) {
        return Result.success(seatService.listSeats(areaId));
    }

    @PostMapping("/seat")
    public Result<Seat> saveSeat(@RequestBody Seat seat) {
        return Result.success(seatService.saveSeat(seat));
    }

    @DeleteMapping("/seat/{id}")
    public Result<String> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return Result.success("删除成功");
    }

    @GetMapping("/booking/list")
    public Result<List<Booking>> listBookings() {
        return Result.success(bookingService.listAllBookings());
    }

    @GetMapping("/violation/list")
    public Result<List<Violation>> listViolations() {
        return Result.success(violationService.listAllViolations());
    }

    @PutMapping("/violation/{id}/handle")
    public Result<String> handleAppeal(@PathVariable Long id, @RequestBody Map<String, Object> params, HttpServletRequest request) {
        Long adminId = (Long) request.getAttribute("userId");
        Boolean approved = (Boolean) params.get("approved");
        String result = params.get("result").toString();
        violationService.handleAppeal(id, adminId, approved, result);
        return Result.success("处理成功");
    }

    @PostMapping("/notification/publish")
    public Result<String> publishAnnouncement(@RequestBody Map<String, String> params) {
        String title = params.get("title");
        String content = params.get("content");
        notificationService.publishAnnouncement(title, content);
        return Result.success("发布成功");
    }

    @GetMapping("/stats/overview")
    public Result<Map<String, Object>> getOverview() {
        Map<String, Object> stats = new HashMap<>();
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getRole, "USER");
        stats.put("userCount", userMapper.selectCount(userWrapper));
        stats.put("seatCount", seatService.listSeats(null).size());
        stats.put("bookingCount", bookingService.listAllBookings().size());
        stats.put("violationCount", violationService.listAllViolations().size());
        return Result.success(stats);
    }

    @GetMapping("/config/list")
    public Result<List<SystemConfig>> listConfigs() {
        return Result.success(systemConfigService.listAll());
    }

    @PutMapping("/config/{id}")
    public Result<String> updateConfig(@PathVariable Long id, @RequestBody Map<String, String> params) {
        systemConfigService.updateConfig(id, params.get("value"));
        return Result.success("更新成功");
    }

    @GetMapping("/timeslot/list")
    public Result<List<TimeSlot>> listTimeSlots() {
        return Result.success(timeSlotService.listAll());
    }

    @PostMapping("/timeslot")
    public Result<TimeSlot> saveTimeSlot(@RequestBody TimeSlot timeSlot) {
        return Result.success(timeSlotService.save(timeSlot));
    }

    @DeleteMapping("/timeslot/{id}")
    public Result<String> deleteTimeSlot(@PathVariable Long id) {
        timeSlotService.delete(id);
        return Result.success("删除成功");
    }
}

