package com.devopsrelease.service;

import com.devopsrelease.entity.ReleaseOrder;
import com.devopsrelease.mapper.ReleaseOrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReleaseOrderService {
    private final ReleaseOrderMapper mapper;

    public PageInfo<ReleaseOrder> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(mapper.selectPage(keyword, status));
    }

    public void save(ReleaseOrder entity) {

        if (entity.getId() == null) {
            mapper.insert(entity);
        } else {
            mapper.update(entity);
        }
    }

    public void delete(Long id) {
        mapper.deleteById(id);
    }


public void updateStatus(Long id, String status) {
    mapper.updateStatus(id, status);
}

}
