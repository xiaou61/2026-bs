package com.powerbank.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powerbank.common.BusinessException;
import com.powerbank.entity.PlacementSite;
import com.powerbank.mapper.PlacementSiteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PlacementSiteService {
    private final PlacementSiteMapper mapper;

    public PageInfo<PlacementSite> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public PlacementSite getById(Long id) {
        PlacementSite entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("投放点位不存在");
        }
        return entity;
    }

    public void save(PlacementSite entity) {
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
