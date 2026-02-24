package com.hrm.mapper;

import com.hrm.entity.Department;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface DepartmentMapper {
    Department selectById(Long id);
    List<Department> selectList(@Param("name") String name, @Param("status") Integer status);
    List<Department> selectAll();
    int insert(Department department);
    int update(Department department);
    int deleteById(Long id);
    int countByParentId(Long parentId);
}
