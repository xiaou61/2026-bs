package com.xiaou.studyroom.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.studyroom.entity.Seat;
import com.xiaou.studyroom.entity.StudyRoom;
import com.xiaou.studyroom.mapper.SeatMapper;
import com.xiaou.studyroom.mapper.StudyRoomMapper;
import com.xiaou.studyroom.service.StudyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class StudyRoomServiceImpl extends ServiceImpl<StudyRoomMapper, StudyRoom> implements StudyRoomService {

    @Autowired
    private SeatMapper seatMapper;

    @Override
    public List<StudyRoom> getAllAvailableRooms() {
        QueryWrapper<StudyRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        return list(queryWrapper);
    }

    @Override
    public Page<StudyRoom> getStudyRoomPage(int current, int size, String keyword) {
        Page<StudyRoom> page = new Page<>(current, size);
        QueryWrapper<StudyRoom> queryWrapper = new QueryWrapper<>();

        if (StrUtil.isNotBlank(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                .like("room_name", keyword)
                .or()
                .like("description", keyword)
            );
        }

        return page(page, queryWrapper);
    }

    @Override
    public boolean isRoomOpen(Long roomId) {
        StudyRoom studyRoom = getById(roomId);
        if (studyRoom == null) {
            return false;
        }

        LocalTime now = LocalTime.now();
        return !now.isBefore(studyRoom.getOpenTime()) && !now.isAfter(studyRoom.getCloseTime());
    }

    @Override
    public StudyRoom getRoomWithSeats(Long roomId) {
        StudyRoom studyRoom = getById(roomId);
        if (studyRoom != null) {
            QueryWrapper<Seat> seatQuery = new QueryWrapper<>();
            seatQuery.eq("room_id", roomId);
            List<Seat> seats = seatMapper.selectList(seatQuery);
            // 可以在StudyRoom实体中添加seats字段来关联座位信息
        }
        return studyRoom;
    }

    @Override
    public boolean updateRoomStatus(Long roomId, Integer status) {
        StudyRoom studyRoom = new StudyRoom();
        studyRoom.setId(roomId);
        studyRoom.setStatus(status);
        return updateById(studyRoom);
    }
}