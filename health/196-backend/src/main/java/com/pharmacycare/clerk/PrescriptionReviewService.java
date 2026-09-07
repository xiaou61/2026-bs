package com.pharmacycare.clerk;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pharmacycare.common.BusinessException;
import com.pharmacycare.entity.PrescriptionReview;
import com.pharmacycare.mapper.PrescriptionReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class PrescriptionReviewService {
    private final PrescriptionReviewMapper mapper;

    public PageInfo<PrescriptionReview> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public PrescriptionReview getById(Long id) {
        PrescriptionReview entity = mapper.selectById(id);
        if (entity == null) {
            throw new BusinessException("处方审核不存在");
        }
        return entity;
    }

    public void save(PrescriptionReview entity) {
        if (!StringUtils.hasText(entity.getRecordName())) {
            throw new BusinessException("审核名称不能为空");
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
