package com.teachres.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialFavorite {
    private Long id;
    private Long materialId;
    private Long userId;
    private LocalDateTime createTime;
}
