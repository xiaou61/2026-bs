package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.PerformanceSchedule;
import com.opera.entity.BookingRecord;
import com.opera.mapper.BookingRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRecordMapper selectionMapper;

    @Autowired
    private PerformanceService scheduleService;

    public PageInfo<BookingRecord> list(Long scheduleId, Long memberId, Integer selectStatus, String role, Long currentUserId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Long targetMemberId = "member".equals(role) ? currentUserId : memberId;
        Long artistId = "artist".equals(role) ? currentUserId : null;
        List<BookingRecord> list = selectionMapper.selectList(scheduleId, targetMemberId, selectStatus, artistId);
        return new PageInfo<>(list);
    }

    public void selectCourse(Long scheduleId, Long memberId) {
        PerformanceSchedule schedule = scheduleService.getById(scheduleId);
        if (schedule == null) {
            throw new BusinessException("排期不存在");
        }
        BookingRecord exists = selectionMapper.selectByScheduleAndStudent(scheduleId, memberId);
        if (exists != null) {
            throw new BusinessException("该剧目已预约");
        }
        if (schedule.getMaxStudentCount() != null && schedule.getSelectedCount() != null && schedule.getSelectedCount() >= schedule.getMaxStudentCount()) {
            throw new BusinessException("该剧目预约名额已满");
        }
        BookingRecord selection = new BookingRecord();
        selection.setScheduleId(scheduleId);
        selection.setCourseId(schedule.getCourseId());
        selection.setMemberId(memberId);
        selection.setSelectStatus(1);
        selectionMapper.insert(selection);
        scheduleService.increaseSelectedCount(scheduleId);
    }
}


