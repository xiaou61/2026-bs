package com.heritageworkshop.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heritageworkshop.common.BusinessException;
import com.heritageworkshop.entity.WorkshopProfile;
import com.heritageworkshop.mapper.WorkshopProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class WorkshopProfileService {
    private final WorkshopProfileMapper mapper;

    public PageInfo<WorkshopProfile> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public WorkshopProfile getById(Long id) {
        WorkshopProfile entity = mapper.selectById(id);
        if (entity == null) throw new BusinessException("工坊档案不存在");
        return entity;
    }

    public void save(WorkshopProfile entity) {
        if (!StringUtils.hasText(entity.getRecordName())) throw new BusinessException("工坊名称不能为空");
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
