package com.xiaou.dto;

import lombok.Data;

@Data
public class ScriptContentDTO {
    private Long id;
    private String chapterName;
    private String roleName;
    private String content;
    private Integer sort;
}
