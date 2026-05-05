package com.meddevice.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.meddevice.entity.DepartmentInfo;
import com.meddevice.mapper.DepartmentInfoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentInfoService {
    private final DepartmentInfoMapper departmentInfoMapper;

    public PageInfo<DepartmentInfo> page(Integer pageNum, Integer pageSize, String keyword, String status) {
        PageHelper.startPage(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        return new PageInfo<>(departmentInfoMapper.selectPage(keyword, status));
    }

    public void save(DepartmentInfo entity) {
        if (entity.getId() == null) departmentInfoMapper.insert(entity);
        else departmentInfoMapper.update(entity);
    }

    public void delete(Long id) {
        departmentInfoMapper.deleteById(id);
    }

    public void updateStatus(Long id, String status) {
        departmentInfoMapper.updateStatus(id, status);
    }
}
