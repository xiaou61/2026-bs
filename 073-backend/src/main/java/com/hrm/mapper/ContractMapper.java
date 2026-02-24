package com.hrm.mapper;

import com.hrm.entity.Contract;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface ContractMapper {
    Contract selectById(Long id);
    List<Contract> selectList(@Param("employeeId") Long employeeId, @Param("employeeName") String employeeName,
                              @Param("type") String type, @Param("status") String status);
    Contract selectByEmployeeId(Long employeeId);
    int insert(Contract contract);
    int update(Contract contract);
    int deleteById(Long id);
}
