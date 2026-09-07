package com.teachres.entity;

import lombok.Data;

@Data
public class MaterialTagRel {
    private Long id;
    private Long materialId;
    private Long tagId;
}
