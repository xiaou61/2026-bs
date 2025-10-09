package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<Category> getTree();
}

