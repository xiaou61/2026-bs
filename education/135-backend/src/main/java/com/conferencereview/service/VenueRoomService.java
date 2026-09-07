package com.conferencereview.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.conferencereview.entity.VenueRoom;
import com.conferencereview.mapper.VenueRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VenueRoomService extends BaseCrudService<VenueRoom> {
    private final VenueRoomMapper inventoryCheckMapper;

    @Override
    protected BaseMapper<VenueRoom> mapper() {
        return inventoryCheckMapper;
    }

    @Override
    protected String[] keywordColumns() {
        return new String[]{"check_no", "lab_name", "consumable_name", "book_qty"};
    }
}

