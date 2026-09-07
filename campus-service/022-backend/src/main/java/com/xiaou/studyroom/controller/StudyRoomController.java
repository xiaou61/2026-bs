package com.xiaou.studyroom.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.studyroom.common.Result;
import com.xiaou.studyroom.entity.StudyRoom;
import com.xiaou.studyroom.service.StudyRoomService;
import com.xiaou.studyroom.utils.AuthHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/room")
@CrossOrigin
public class StudyRoomController {

    @Autowired
    private StudyRoomService studyRoomService;

    @Autowired
    private AuthHelper authHelper;

    @GetMapping("/list")
    public Result<List<StudyRoom>> getAllAvailableRooms() {
        List<StudyRoom> rooms = studyRoomService.getAllAvailableRooms();
        return Result.success(rooms);
    }

    @GetMapping("/page")
    public Result<Page<StudyRoom>> getStudyRoomPage(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        Page<StudyRoom> page = studyRoomService.getStudyRoomPage(current, size, keyword);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    public Result<StudyRoom> getStudyRoomById(@PathVariable Long id) {
        StudyRoom studyRoom = studyRoomService.getRoomWithSeats(id);
        if (studyRoom != null) {
            return Result.success(studyRoom);
        }
        return Result.error("自习室不存在");
    }

    @PostMapping
    public Result<String> createStudyRoom(@RequestHeader("Authorization") String token, @Valid @RequestBody StudyRoom studyRoom) {
        authHelper.requireAdmin(token);
        studyRoom.setStatus(1);
        if (studyRoomService.save(studyRoom)) {
            return Result.success("自习室创建成功");
        }
        return Result.error("自习室创建失败");
    }

    @PutMapping("/{id}")
    public Result<String> updateStudyRoom(@RequestHeader("Authorization") String token,
                                          @PathVariable Long id,
                                          @Valid @RequestBody StudyRoom studyRoom) {
        authHelper.requireAdmin(token);
        studyRoom.setId(id);
        if (studyRoomService.updateById(studyRoom)) {
            return Result.success("自习室更新成功");
        }
        return Result.error("自习室更新失败");
    }

    @PutMapping("/{id}/status")
    public Result<String> updateStudyRoomStatus(@RequestHeader("Authorization") String token,
                                                @PathVariable Long id,
                                                @RequestParam Integer status) {
        authHelper.requireAdmin(token);
        if (studyRoomService.updateRoomStatus(id, status)) {
            String statusText = status == 1 ? "开放" : "关闭";
            return Result.success("自习室状态更新为：" + statusText);
        }
        return Result.error("自习室状态更新失败");
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteStudyRoom(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        authHelper.requireAdmin(token);
        if (studyRoomService.removeById(id)) {
            return Result.success("自习室删除成功");
        }
        return Result.error("自习室删除失败");
    }

    @GetMapping("/{id}/status")
    public Result<Boolean> checkRoomStatus(@PathVariable Long id) {
        boolean isOpen = studyRoomService.isRoomOpen(id);
        return Result.success(isOpen);
    }
}
