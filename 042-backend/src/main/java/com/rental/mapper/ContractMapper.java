package com.rental.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rental.entity.Contract;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContractMapper extends BaseMapper<Contract> {
}
