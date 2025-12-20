package com.xiaou.snack.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaou.snack.wms.entity.basic.Sku;
import com.xiaou.snack.wms.mapper.SkuMapper;
import com.xiaou.snack.wms.service.SkuService;
import org.springframework.stereotype.Service;

@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {
}
