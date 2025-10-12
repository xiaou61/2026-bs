package com.xiaou.vo;

import com.xiaou.entity.CookingRecord;
import lombok.Data;

@Data
public class CookingRecordVO extends CookingRecord {
    private String recipeName;
    private String recipeImage;
}

