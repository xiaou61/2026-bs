package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oa.entity.Meeting;
import com.oa.entity.MeetingRoom;
import com.oa.entity.User;
import com.oa.mapper.MeetingMapper;
import com.oa.mapper.MeetingRoomMapper;
import com.oa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingMapper meetingMapper;
    private final MeetingRoomMapper meetingRoomMapper;
    private final UserMapper userMapper;

    public Page<Meeting> getList(Integer pageNum, Integer pageSize, String date) {
        Page<Meeting> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Meeting> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(date)) {
            LocalDate d = LocalDate.parse(date);
            wrapper.apply("DATE(start_time) = {0}", d);
        }
        wrapper.orderByDesc(Meeting::getStartTime);
        Page<Meeting> result = meetingMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillInfo);
        return result;
    }

    public List<Meeting> getMyMeetings(Long userId, String date) {
        LambdaQueryWrapper<Meeting> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.eq(Meeting::getOrganizerId, userId).or().like(Meeting::getParticipants, userId.toString()));
        if (StringUtils.hasText(date)) {
            LocalDate d = LocalDate.parse(date);
            wrapper.apply("DATE(start_time) = {0}", d);
        }
        wrapper.orderByAsc(Meeting::getStartTime);
        List<Meeting> list = meetingMapper.selectList(wrapper);
        list.forEach(this::fillInfo);
        return list;
    }

    private void fillInfo(Meeting meeting) {
        if (meeting.getRoomId() != null) {
            MeetingRoom room = meetingRoomMapper.selectById(meeting.getRoomId());
            if (room != null) meeting.setRoomName(room.getName());
        }
        User organizer = userMapper.selectById(meeting.getOrganizerId());
        if (organizer != null) meeting.setOrganizerName(organizer.getRealName());
    }

    public void add(Long userId, Meeting meeting) {
        meeting.setOrganizerId(userId);
        meeting.setStatus(0);
        meetingMapper.insert(meeting);
    }

    public void update(Meeting meeting) {
        meetingMapper.updateById(meeting);
    }

    public void delete(Long id) {
        meetingMapper.deleteById(id);
    }
}
