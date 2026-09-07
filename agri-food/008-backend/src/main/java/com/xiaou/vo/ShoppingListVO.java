package com.xiaou.vo;

import com.xiaou.entity.ShoppingList;
import lombok.Data;

@Data
public class ShoppingListVO extends ShoppingList {
    private String ingredientName;
}

