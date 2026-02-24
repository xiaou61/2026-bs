package com.hrm.mapper;

import com.hrm.entity.Employee;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface EmployeeMapper {
    Employee selectById(Long id);
    List<Employee> selectList(@Param("name") String name, @Param("empNo") String empNo,
                              @Param("departmentId") Long departmentId, @Param("status") String status);
    List<Employee> selectAll();
    int insert(Employee employee);
    int update(Employee employee);
    int deleteById(Long id);
    String selectMaxEmpNo();
    int countByDepartmentId(Long departmentId);
    int countByPositionId(Long positionId);
    int countByStatus(String status);
    int countAll();
}
