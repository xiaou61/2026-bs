package com.xiaou.studyroom.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.studyroom.entity.Seat;
import com.xiaou.studyroom.entity.StudyRoom;
import com.xiaou.studyroom.entity.Reservation;
import com.xiaou.studyroom.mapper.ReservationMapper;
import com.xiaou.studyroom.mapper.SeatMapper;
import com.xiaou.studyroom.mapper.StudyRoomMapper;
import com.xiaou.studyroom.service.StudyRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class StudyRoomServiceImpl extends ServiceImpl<StudyRoomMapper, StudyRoom> implements StudyRoomService {

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public List<StudyRoom> getAllAvailableRooms() {
        QueryWrapper<StudyRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        List<StudyRoom> rooms = list(queryWrapper);
        rooms.forEach(this::fillSeatSummary);
        return rooms;
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

        Page<StudyRoom> result = page(page, queryWrapper);
        result.getRecords().forEach(this::fillSeatSummary);
        return result;
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
            List<Seat> seats = loadSeatsWithRealtimeStatus(roomId);
            studyRoom.setSeats(seats);
            studyRoom.setAvailableSeats((int) seats.stream().filter(seat -> seat.getSeatStatus() != null && seat.getSeatStatus() == 1).count());
            studyRoom.setOccupiedSeats((int) seats.stream().filter(seat -> seat.getSeatStatus() != null && seat.getSeatStatus() == 2).count());
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

    private void fillSeatSummary(StudyRoom room) {
        List<Seat> seats = loadSeatsWithRealtimeStatus(room.getId());
        room.setSeats(seats);
        room.setAvailableSeats((int) seats.stream().filter(seat -> seat.getSeatStatus() != null && seat.getSeatStatus() == 1).count());
        room.setOccupiedSeats((int) seats.stream().filter(seat -> seat.getSeatStatus() != null && seat.getSeatStatus() == 2).count());
    }

    private List<Seat> loadSeatsWithRealtimeStatus(Long roomId) {
        QueryWrapper<Seat> seatQuery = new QueryWrapper<>();
        seatQuery.eq("room_id", roomId);
        List<Seat> seats = seatMapper.selectList(seatQuery);
        LocalDateTime now = LocalDateTime.now();
        seats.forEach(seat -> seat.setSeatStatus(resolveSeatStatus(seat, now)));
        return seats;
    }

    private Integer resolveSeatStatus(Seat seat, LocalDateTime now) {
        if (seat.getSeatStatus() != null && seat.getSeatStatus() == 3) {
            return 3;
        }

        QueryWrapper<Reservation> reservationQuery = new QueryWrapper<>();
        reservationQuery.eq("seat_id", seat.getId())
                .eq("status", 2)
                .le("start_time", now)
                .ge("end_time", now);

        long activeReservationCount = reservationMapper.selectCount(reservationQuery);
        return activeReservationCount > 0 ? 2 : 1;
    }
}
