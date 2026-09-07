package com.heritageworkshop.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heritageworkshop.common.BusinessException;
import com.heritageworkshop.entity.CourseBooking;
import com.heritageworkshop.mapper.CourseBookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class CourseBookingService {
    private final CourseBookingMapper mapper;

    public PageInfo<CourseBooking> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public CourseBooking getById(Long id) {
        CourseBooking entity = mapper.selectById(id);
        if (entity == null) throw new BusinessException("课程预约不存在");
        return entity;
    }

    public void save(CourseBooking entity) {
        if (!StringUtils.hasText(entity.getRecordName())) throw new BusinessException("预约名称不能为空");
        if (entity.getId() == null) {
            if (entity.getStatus() == null) entity.setStatus("REGISTERED");
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
