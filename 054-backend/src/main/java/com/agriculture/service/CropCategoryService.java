package com.agriculture.service;

import com.agriculture.entity.CropCategory;
import com.agriculture.mapper.CropCategoryMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CropCategoryService extends ServiceImpl<CropCategoryMapper, CropCategory> {

    public List<CropCategory> getList() {
        return this.lambdaQuery().orderByAsc(CropCategory::getSort).list();
    }

    public List<CropCategory> getTree() {
        return this.lambdaQuery().eq(CropCategory::getParentId, 0).orderByAsc(CropCategory::getSort).list();
    }
}
