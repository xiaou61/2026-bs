package com.opera.service;

import com.opera.entity.BookingRecord;
import com.opera.mapper.BookingRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingRecordMapperAdapter {

    @Autowired
    private BookingRecordMapper selectionMapper;

    public BookingRecord getById(Long id) {
        return selectionMapper.selectById(id);
    }
}


