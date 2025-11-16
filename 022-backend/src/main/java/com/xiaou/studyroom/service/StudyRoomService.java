package com.xiaou.studyroom.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.studyroom.entity.StudyRoom;

import java.util.List;

public interface StudyRoomService extends IService<StudyRoom> {

    List<StudyRoom> getAllAvailableRooms();

    Page<StudyRoom> getStudyRoomPage(int current, int size, String keyword);

    boolean isRoomOpen(Long roomId);

    StudyRoom getRoomWithSeats(Long roomId);

    boolean updateRoomStatus(Long roomId, Integer status);
}