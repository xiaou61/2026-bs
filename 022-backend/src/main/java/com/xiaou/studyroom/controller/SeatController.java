package com.xiaou.studyroom.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.studyroom.common.Result;
import com.xiaou.studyroom.entity.Seat;
import com.xiaou.studyroom.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/seat")
@CrossOrigin
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/available/{roomId}")
    public Result<List<Seat>> getAvailableSeats(@PathVariable Long roomId) {
        List<Seat> seats = seatService.getAvailableSeats(roomId);
        return Result.success(seats);
    }

    @GetMapping("/room/{roomId}")
    public Result<List<Seat>> getSeatsByRoomId(@PathVariable Long roomId) {
        List<Seat> seats = seatService.getSeatsWithStatus(roomId);
        return Result.success(seats);
    }

    @GetMapping("/page")
    public Result<Page<Seat>> getSeatPage(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long roomId,
            @RequestParam(required = false) Integer seatType,
            @RequestParam(required = false) Integer status) {
        Page<Seat> page = seatService.getSeatPage(current, size, roomId, seatType, status);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<Seat> getSeatById(@PathVariable Long id) {
        Seat seat = seatService.getById(id);
        if (seat != null) {
            return Result.success(seat);
        }
        return Result.error("座位不存在");
    }

    @PostMapping
    public Result<String> createSeat(@Valid @RequestBody Seat seat) {
        seat.setSeatStatus(1); // 默认为空闲状态
        if (seatService.save(seat)) {
            return Result.success("座位添加成功");
        }
        return Result.error("座位添加失败");
    }

    @PutMapping("/{id}")
    public Result<String> updateSeat(@PathVariable Long id, @Valid @RequestBody Seat seat) {
        seat.setId(id);
        if (seatService.updateById(seat)) {
            return Result.success("座位更新成功");
        }
        return Result.error("座位更新失败");
    }

    @PutMapping("/{id}/status")
    public Result<String> updateSeatStatus(@PathVariable Long id, @RequestParam Integer status) {
        if (seatService.updateSeatStatus(id, status)) {
            String statusText = getStatusText(status);
            return Result.success("座位状态更新为：" + statusText);
        }
        return Result.error("座位状态更新失败");
    }

    @PutMapping("/batch/status")
    public Result<String> batchUpdateSeatStatus(@RequestBody List<Long> seatIds, @RequestParam Integer status) {
        if (seatService.batchUpdateSeatStatus(seatIds, status)) {
            String statusText = getStatusText(status);
            return Result.success("批量更新座位状态成功，状态为：" + statusText);
        }
        return Result.error("批量更新座位状态失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteSeat(@PathVariable Long id) {
        if (seatService.removeById(id)) {
            return Result.success("座位删除成功");
        }
        return Result.error("座位删除失败");
    }

    @GetMapping("/count/available/{roomId}")
    public Result<Integer> getAvailableSeatCount(@PathVariable Long roomId) {
        int count = seatService.getAvailableSeatCount(roomId);
        return Result.success(count);
    }

    private String getStatusText(Integer status) {
        switch (status) {
            case 1:
                return "空闲";
            case 2:
                return "占用";
            case 3:
                return "维修";
            default:
                return "未知";
        }
    }
}