package com.housekeeping.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.housekeeping.common.BusinessException;
import com.housekeeping.entity.ServiceRecord;
import com.housekeeping.mapper.ServiceRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ServiceRecordService {
    private final ServiceRecordMapper mapper;

    public PageInfo<ServiceRecord> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public ServiceRecord getById(Long id) {
        ServiceRecord entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("服务记录不存在");
        }
        return entity;
    }

    public void save(ServiceRecord entity) {
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
