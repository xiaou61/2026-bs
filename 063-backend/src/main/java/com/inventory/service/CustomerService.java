package com.inventory.service;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inventory.common.BusinessException;
import com.inventory.common.PageResult;
import com.inventory.entity.Customer;
import com.inventory.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerService {

    @Resource
    private CustomerMapper customerMapper;

    public PageResult<Customer> page(Integer pageNum, Integer pageSize, String name, String contactPerson, Integer status) {
        PageHelper.startPage(pageNum, pageSize);
        List<Customer> list = customerMapper.selectPageList(name, contactPerson, status);
        PageInfo<Customer> pageInfo = new PageInfo<>(list);
        PageResult<Customer> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setRecords(pageInfo.getList());
        return result;
    }

    public List<Customer> list() {
        return customerMapper.selectEnabledList();
    }

    public void save(Customer customer) {
        if (customer == null || customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new BusinessException("客户名称不能为空");
        }
        customer.setName(customer.getName().trim());
        if (customer.getContactPerson() != null) {
            customer.setContactPerson(customer.getContactPerson().trim());
        }
        if (customer.getPhone() != null) {
            customer.setPhone(customer.getPhone().trim());
        }
        if (customer.getAddress() != null) {
            customer.setAddress(customer.getAddress().trim());
        }
        if (customer.getStatus() == null) {
            customer.setStatus(1);
        }
        if (customer.getId() == null) {
            customer.setCustomerNo("CUS" + IdUtil.getSnowflakeNextIdStr());
            customerMapper.insert(customer);
        } else {
            Customer db = customerMapper.selectById(customer.getId());
            if (db == null) {
                throw new BusinessException("客户不存在");
            }
            if (customer.getCustomerNo() == null || customer.getCustomerNo().trim().isEmpty()) {
                customer.setCustomerNo(db.getCustomerNo());
            } else {
                customer.setCustomerNo(customer.getCustomerNo().trim());
            }
            customerMapper.update(customer);
        }
    }

    public void deleteById(Long id) {
        customerMapper.deleteById(id);
    }

    public Customer mustGetById(Long id) {
        Customer customer = customerMapper.selectById(id);
        if (customer == null) {
            throw new BusinessException("客户不存在");
        }
        return customer;
    }

    public Long countAll() {
        Long count = customerMapper.countAll();
        return count == null ? 0L : count;
    }
}
