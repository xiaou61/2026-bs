package com.hospital.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hospital.common.BusinessException;
import com.hospital.entity.DepartmentInfo;
import com.hospital.mapper.DepartmentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentInfoMapper departmentInfoMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private OperationLogService operationLogService;

    public PageInfo<DepartmentInfo> page(String name, Integer status, Integer pageNum, Integer pageSize, String role) {
        authService.assertAdmin(role);
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(departmentInfoMapper.selectPage(name, status));
    }

    public List<DepartmentInfo> enabledList() {
        return departmentInfoMapper.selectEnabled();
    }

    public void save(DepartmentInfo entity, Long userId, String role) {
        authService.assertAdmin(role);
        if (entity == null || !StringUtils.hasText(entity.getName()) || !StringUtils.hasText(entity.getCode())) {
            throw new BusinessException("科室名称和编码不能为空");
        }
        if (entity.getId() == null) {
            entity.setSort(entity.getSort() == null ? 0 : entity.getSort());
            entity.setStatus(entity.getStatus() == null ? 1 : entity.getStatus());
            entity.setCreateTime(LocalDateTime.now());
            entity.setUpdateTime(LocalDateTime.now());
            departmentInfoMapper.insert(entity);
            operationLogService.record(userId, role, "科室", "新增", "新增科室");
            return;
        }
        entity.setUpdateTime(LocalDateTime.now());
        departmentInfoMapper.update(entity);
        operationLogService.record(userId, role, "科室", "编辑", "编辑科室");
    }

    public void delete(Long id, Long userId, String role) {
        authService.assertAdmin(role);
        departmentInfoMapper.deleteById(id);
        operationLogService.record(userId, role, "科室", "删除", "删除科室");
    }

    public DepartmentInfo getById(Long id) {
        DepartmentInfo department = departmentInfoMapper.selectById(id);
        if (department == null) {
            throw new BusinessException("科室不存在");
        }
        return department;
    }

    public long countAll() {
        return departmentInfoMapper.countAll();
    }
}
