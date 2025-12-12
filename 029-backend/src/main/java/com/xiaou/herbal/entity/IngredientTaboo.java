package com.xiaou.herbal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ingredient_taboo")
public class IngredientTaboo implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long ingredientId1;

    private Long ingredientId2;

    private String reason;

    private LocalDateTime createTime;
}
