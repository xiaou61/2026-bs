package com.xiaou.wedding.mapper;

import com.xiaou.wedding.entity.Customer;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface CustomerMapper {
    Customer findById(@Param("id") Long id);

    List<Customer> search(@Param("keyword") String keyword);

    int insert(Customer customer);

    int update(Customer customer);

    int logicDelete(@Param("id") Long id);
}
