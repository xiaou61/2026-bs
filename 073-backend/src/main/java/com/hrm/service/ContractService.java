package com.hrm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.entity.Contract;
import com.hrm.mapper.ContractMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class ContractService {
    @Resource
    private ContractMapper contractMapper;

    public Contract getById(Long id) {
        return contractMapper.selectById(id);
    }

    public PageInfo<Contract> getList(Long employeeId, String employeeName, String type, String status,
                                       Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(contractMapper.selectList(employeeId, employeeName, type, status));
    }

    public Contract getByEmployeeId(Long employeeId) {
        return contractMapper.selectByEmployeeId(employeeId);
    }

    public void add(Contract contract) {
        contract.setStatus("active");
        contractMapper.insert(contract);
    }

    public void update(Contract contract) {
        contractMapper.update(contract);
    }

    public void delete(Long id) {
        contractMapper.deleteById(id);
    }

    public void terminate(Long id) {
        Contract contract = new Contract();
        contract.setId(id);
        contract.setStatus("terminated");
        contractMapper.update(contract);
    }
}
