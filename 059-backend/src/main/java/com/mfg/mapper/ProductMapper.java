package com.mfg.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mfg.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
