package com.xiaou.wedding.service;

import com.xiaou.wedding.entity.Customer;
import java.util.List;

public interface CustomerService {
    List<Customer> search(String keyword);

    Customer detail(Long id);

    void create(Customer customer);

    void update(Customer customer);

    void delete(Long id);
}
