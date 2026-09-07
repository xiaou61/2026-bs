package com.xiaou.wedding.service.impl;

import com.xiaou.wedding.entity.Customer;
import com.xiaou.wedding.exception.BusinessException;
import com.xiaou.wedding.mapper.CustomerMapper;
import com.xiaou.wedding.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Customer> search(String keyword) {
        return customerMapper.search(keyword);
    }

    @Override
    public Customer detail(Long id) {
        Customer customer = customerMapper.findById(id);
        if (customer == null) {
            throw new BusinessException("Customer not found");
        }
        return customer;
    }

    @Override
    public void create(Customer customer) {
        customerMapper.insert(customer);
    }

    @Override
    public void update(Customer customer) {
        if (customerMapper.findById(customer.getId()) == null) {
            throw new BusinessException("Customer not found");
        }
        customerMapper.update(customer);
    }

    @Override
    public void delete(Long id) {
        if (customerMapper.findById(id) == null) {
            throw new BusinessException("Customer not found");
        }
        customerMapper.logicDelete(id);
    }
}
