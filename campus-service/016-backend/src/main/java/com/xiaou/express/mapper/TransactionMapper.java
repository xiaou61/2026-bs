package com.xiaou.express.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.express.entity.Transaction;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionMapper extends BaseMapper<Transaction> {
}

