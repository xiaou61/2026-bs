package com.xiaou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaou.dto.ShoppingGenerateDTO;
import com.xiaou.entity.ShoppingList;
import com.xiaou.vo.ShoppingListVO;

import java.util.List;

public interface ShoppingListService extends IService<ShoppingList> {
    
    void generateShoppingList(Long userId, ShoppingGenerateDTO dto);
    
    List<ShoppingListVO> getShoppingList(Long userId);
    
    void checkItem(Long id, Long userId);
    
    void addToStock(Long userId);
    
    void clearList(Long userId);
}

