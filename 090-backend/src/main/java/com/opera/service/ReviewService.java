package com.opera.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opera.common.BusinessException;
import com.opera.entity.BookingRecord;
import com.opera.entity.ReviewRecord;
import com.opera.mapper.ReviewRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRecordMapper scoreMapper;

    @Autowired
    private BookingRecordMapperAdapter selectionAdapter;

    @Autowired
    private AuthService authService;

    public PageInfo<ReviewRecord> list(Long scheduleId, Long memberId, Long userId, String role, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ReviewRecord> list = scoreMapper.selectList(scheduleId, "member".equals(role) ? userId : memberId, "artist".equals(role) ? userId : null);
        return new PageInfo<>(list);
    }

    public void save(ReviewRecord entity, Long userId, String role) {
        authService.assertTeacher(role);
        if (entity == null || entity.getSelectionId() == null) {
            throw new BusinessException("预约记录不能为空");
        }
        BookingRecord selection = selectionAdapter.getById(entity.getSelectionId());
        if (selection == null) {
            throw new BusinessException("预约记录不存在");
        }
        BigDecimal usual = entity.getUsualScore() == null ? BigDecimal.ZERO : entity.getUsualScore();
        BigDecimal exam = entity.getExamScore() == null ? BigDecimal.ZERO : entity.getExamScore();
        BigDecimal total = usual.multiply(new BigDecimal("0.4")).add(exam.multiply(new BigDecimal("0.6"))).setScale(2, RoundingMode.HALF_UP);
        entity.setArtistId(userId);
        entity.setScheduleId(selection.getScheduleId());
        entity.setCourseId(selection.getCourseId());
        entity.setMemberId(selection.getMemberId());
        entity.setTotalScore(total);
        entity.setGradeLevel(resolveLevel(total));
        ReviewRecord exists = scoreMapper.selectBySelectionId(entity.getSelectionId());
        if (exists == null) {
            scoreMapper.insert(entity);
        } else {
            scoreMapper.update(entity);
        }
    }

    private String resolveLevel(BigDecimal total) {
        if (total.compareTo(new BigDecimal("90")) >= 0) return "A";
        if (total.compareTo(new BigDecimal("80")) >= 0) return "B";
        if (total.compareTo(new BigDecimal("70")) >= 0) return "C";
        if (total.compareTo(new BigDecimal("60")) >= 0) return "D";
        return "E";
    }
}


