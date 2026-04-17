package com.xiaou.studyroom.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.studyroom.common.Result;
import com.xiaou.studyroom.entity.Seat;
import com.xiaou.studyroom.service.SeatService;
import com.xiaou.studyroom.utils.AuthHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/seat")
@CrossOrigin
public class SeatController {

    @Autowired
    private SeatService seatService;

    @Autowired
    private AuthHelper authHelper;

    @GetMapping("/list")
    public Result<List<Seat>> getSeatList(@RequestParam Long roomId,
                                          @RequestParam(required = false) Integer seatType,
                                          @RequestParam(required = false) Integer status) {
        List<Seat> seats = seatService.getSeatsWithStatus(roomId);
        if (seatType != null) {
            seats = seats.stream().filter(seat -> seatType.equals(seat.getSeatType())).toList();
        }
        if (status != null) {
            seats = seats.stream().filter(seat -> status.equals(seat.getSeatStatus())).toList();
        }
        return Result.success(seats);
    }

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
    public Result<String> createSeat(@RequestHeader("Authorization") String token, @Valid @RequestBody Seat seat) {
        authHelper.requireAdmin(token);
        seat.setSeatStatus(1); // 默认为空闲状态
        if (seatService.save(seat)) {
            return Result.success("座位添加成功");
        }
        return Result.error("座位添加失败");
    }

    @PutMapping("/{id}")
    public Result<String> updateSeat(@RequestHeader("Authorization") String token,
                                     @PathVariable Long id,
                                     @Valid @RequestBody Seat seat) {
        authHelper.requireAdmin(token);
        seat.setId(id);
        if (seatService.updateById(seat)) {
            return Result.success("座位更新成功");
        }
        return Result.error("座位更新失败");
    }

    @PutMapping("/{id}/status")
    public Result<String> updateSeatStatus(@RequestHeader("Authorization") String token,
                                           @PathVariable Long id,
                                           @RequestParam Integer status) {
        authHelper.requireAdmin(token);
        if (seatService.updateSeatStatus(id, status)) {
            String statusText = getStatusText(status);
            return Result.success("座位状态更新为：" + statusText);
        }
        return Result.error("座位状态更新失败");
    }

    @PutMapping("/batch/status")
    public Result<String> batchUpdateSeatStatus(@RequestHeader("Authorization") String token,
                                                @RequestBody List<Long> seatIds,
                                                @RequestParam Integer status) {
        authHelper.requireAdmin(token);
        if (seatService.batchUpdateSeatStatus(seatIds, status)) {
            String statusText = getStatusText(status);
            return Result.success("批量更新座位状态成功，状态为：" + statusText);
        }
        return Result.error("批量更新座位状态失败");
    }

    @PostMapping("/batch-create")
    public Result<String> batchCreateSeat(@RequestHeader("Authorization") String token,
                                          @RequestBody Map<String, Object> body) {
        authHelper.requireAdmin(token);
        Long roomId = Long.valueOf(body.get("roomId").toString());
        String prefix = String.valueOf(body.getOrDefault("prefix", "S"));
        int startNumber = Integer.parseInt(body.getOrDefault("startNumber", 1).toString());
        int endNumber = Integer.parseInt(body.getOrDefault("endNumber", startNumber).toString());
        Integer seatType = Integer.valueOf(body.getOrDefault("seatType", 1).toString());

        List<Seat> seats = IntStream.rangeClosed(startNumber, endNumber)
                .mapToObj(number -> {
                    Seat seat = new Seat();
                    seat.setRoomId(roomId);
                    seat.setSeatNumber(prefix + String.format("%02d", number));
                    seat.setSeatType(seatType);
                    seat.setSeatStatus(1);
                    return seat;
                })
                .toList();

        return seatService.saveBatch(seats) ? Result.success("批量添加座位成功") : Result.error("批量添加座位失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteSeat(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        authHelper.requireAdmin(token);
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

    @GetMapping("/realtime/{roomId}")
    public Result<List<Seat>> getRealtimeSeats(@PathVariable Long roomId) {
        return Result.success(seatService.getSeatsWithStatus(roomId));
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
