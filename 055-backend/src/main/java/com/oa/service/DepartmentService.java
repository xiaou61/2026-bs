package com.oa.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oa.entity.Department;
import com.oa.entity.User;
import com.oa.mapper.DepartmentMapper;
import com.oa.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentMapper departmentMapper;
    private final UserMapper userMapper;

    public List<Department> getList() {
        List<Department> list = departmentMapper.selectList(new LambdaQueryWrapper<Department>().orderByAsc(Department::getSort));
        list.forEach(d -> {
            if (d.getLeaderId() != null) {
                User leader = userMapper.selectById(d.getLeaderId());
                if (leader != null) d.setLeaderName(leader.getRealName());
            }
        });
        return list;
    }

    public List<Department> getTree() {
        List<Department> all = getList();
        return buildTree(all, 0L);
    }

    private List<Department> buildTree(List<Department> all, Long parentId) {
        return all.stream()
                .filter(d -> parentId.equals(d.getParentId()))
                .peek(d -> d.setChildren(buildTree(all, d.getId())))
                .collect(Collectors.toList());
    }

    public void add(Department department) {
        departmentMapper.insert(department);
    }

    public void update(Department department) {
        departmentMapper.updateById(department);
    }

    public void delete(Long id) {
        long count = departmentMapper.selectCount(new LambdaQueryWrapper<Department>().eq(Department::getParentId, id));
        if (count > 0) {
            throw new RuntimeException("存在子部门，无法删除");
        }
        departmentMapper.deleteById(id);
    }
}
