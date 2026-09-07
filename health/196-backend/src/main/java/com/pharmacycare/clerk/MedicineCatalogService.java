package com.pharmacycare.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pharmacycare.common.BusinessException;
import com.pharmacycare.entity.MedicineCatalog;
import com.pharmacycare.mapper.MedicineCatalogMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MedicineCatalogService {
    private final MedicineCatalogMapper mapper;

    public PageInfo<MedicineCatalog> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public MedicineCatalog getById(Long id) {
        MedicineCatalog entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("药品目录不存在");
        }
        return entity;
    }

    public void save(MedicineCatalog entity) {
        if (!StringUtils.hasText(entity.getRecordName())) {
            throw new BusinessException("药品名称不能为空");
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
