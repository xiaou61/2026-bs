package com.knowledgeqa.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TableMeta {
    private String table;
    private String columns;
    private String orderBy;
    private List<String> keywordColumns;
}
