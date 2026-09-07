package com.pharmacycare.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pharmacycare.common.BusinessException;
import com.pharmacycare.entity.PharmacyStore;
import com.pharmacycare.mapper.PharmacyStoreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PharmacyStoreService {
    private final PharmacyStoreMapper mapper;

    public PageInfo<PharmacyStore> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public PharmacyStore getById(Long id) {
        PharmacyStore entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("药店门店不存在");
        }
        return entity;
    }

    public void save(PharmacyStore entity) {
        if (!StringUtils.hasText(entity.getRecordName())) {
            throw new BusinessException("门店名称不能为空");
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
