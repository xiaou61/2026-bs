package com.psychologycare.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.psychologycare.entity.CounselRoom;
import com.psychologycare.mapper.CounselRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CounselRoomService extends BaseCrudService<CounselRoom> {
    private final CounselRoomMapper counselRoomMapper;

    @Override
    protected BaseMapper<CounselRoom> mapper() {
        return counselRoomMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"room_no", "room_name", "room_type", "campus_name"};
    }
}







