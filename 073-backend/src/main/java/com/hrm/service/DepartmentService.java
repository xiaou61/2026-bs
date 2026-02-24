package com.hrm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.common.BusinessException;
import com.hrm.entity.Department;
import com.hrm.mapper.DepartmentMapper;
import com.hrm.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    @Resource
    private DepartmentMapper departmentMapper;
    @Resource
    private EmployeeMapper employeeMapper;

    public Department getById(Long id) {
        return departmentMapper.selectById(id);
    }

    public PageInfo<Department> getList(String name, Integer status, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(departmentMapper.selectList(name, status));
    }

    public List<Department> getAll() {
        return departmentMapper.selectAll();
    }

    public List<Department> getTree() {
        List<Department> all = departmentMapper.selectList(null, null);
        return buildTree(all, 0L);
    }

    private List<Department> buildTree(List<Department> list, Long parentId) {
        List<Department> result = new ArrayList<>();
        for (Department dept : list) {
            if (parentId.equals(dept.getParentId())) {
                dept.setChildren(buildTree(list, dept.getId()));
                result.add(dept);
            }
        }
        return result;
    }

    public void add(Department department) {
        department.setStatus(1);
        departmentMapper.insert(department);
    }

    public void update(Department department) {
        departmentMapper.update(department);
    }

    public void delete(Long id) {
        if (departmentMapper.countByParentId(id) > 0) {
            throw new BusinessException("存在子部门，无法删除");
        }
        if (employeeMapper.countByDepartmentId(id) > 0) {
            throw new BusinessException("部门下存在员工，无法删除");
        }
        departmentMapper.deleteById(id);
    }
}
