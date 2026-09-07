package com.housekeeping.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.housekeeping.common.BusinessException;
import com.housekeeping.entity.BookingReview;
import com.housekeeping.mapper.BookingReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class BookingReviewService {
    private final BookingReviewMapper mapper;

    public PageInfo<BookingReview> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public BookingReview getById(Long id) {
        BookingReview entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("预约审核记录不存在");
        }
        return entity;
    }

    public void save(BookingReview entity) {
        if (!StringUtils.hasText(entity.getRecordName())) {
            throw new BusinessException("记录名称不能为空");
        }
        if (entity.getId() == null) {
            if (!StringUtils.hasText(entity.getStatus())) {
                entity.setStatus("REGISTERED");
            }
            mapper.insert(entity);
        } else {
            getById(entity.getId());
            mapper.update(entity);
        }
    }

    public void delete(Long id) {
        getById(id);
        mapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        getById(id);
        mapper.updateStatus(id, status);
    }
}
