package com.hrm.mapper;

import com.hrm.entity.Salary;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SalaryMapper {
    Salary selectById(Long id);
    List<Salary> selectList(@Param("employeeId") Long employeeId, @Param("employeeName") String employeeName,
                            @Param("yearMonth") String yearMonth, @Param("status") String status);
    int insert(Salary salary);
    int update(Salary salary);
    int deleteById(Long id);
}
