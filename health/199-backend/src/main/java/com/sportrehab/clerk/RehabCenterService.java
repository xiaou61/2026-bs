package com.sportrehab.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sportrehab.common.BusinessException;
import com.sportrehab.entity.RehabCenter;
import com.sportrehab.mapper.RehabCenterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class RehabCenterService {
    private final RehabCenterMapper mapper;

    public PageInfo<RehabCenter> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public RehabCenter getById(Long id) {
        RehabCenter entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("康复中心记录不存在");
        }
        return entity;
    }

    public void save(RehabCenter entity) {
        if (!StringUtils.hasText(entity.getRecordName())) {
            throw new BusinessException("记录名称不能为空");
        }
        if (entity.getId() == null) {
            if (entity.getStatus() == null) {
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
