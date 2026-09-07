package com.xiaou.snack.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.snack.wms.entity.basic.Customer;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
}
