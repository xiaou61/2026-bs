package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oa.entity.MeetingRoom;
import com.oa.mapper.MeetingRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingRoomService {
    private final MeetingRoomMapper meetingRoomMapper;

    public Page<MeetingRoom> getList(Integer pageNum, Integer pageSize) {
        Page<MeetingRoom> page = new Page<>(pageNum, pageSize);
        return meetingRoomMapper.selectPage(page, new LambdaQueryWrapper<MeetingRoom>().orderByDesc(MeetingRoom::getCreateTime));
    }

    public List<MeetingRoom> getAll() {
        return meetingRoomMapper.selectList(new LambdaQueryWrapper<MeetingRoom>().eq(MeetingRoom::getStatus, 1));
    }

    public void add(MeetingRoom room) {
        meetingRoomMapper.insert(room);
    }

    public void update(MeetingRoom room) {
        meetingRoomMapper.updateById(room);
    }

    public void delete(Long id) {
        meetingRoomMapper.deleteById(id);
    }
}
