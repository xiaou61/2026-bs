package com.textintegrity.mapper;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommonSqlProvider {

    public String selectPage(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ").append(params.get("columns")).append(" from ").append(params.get("table")).append(" where 1=1");
        appendFilters(sql, params);
        String keyword = (String) params.get("keyword");
        List<String> keywordColumns = (List<String>) params.get("keywordColumns");
        if (StringUtils.hasText(keyword) && keywordColumns != null && !keywordColumns.isEmpty()) {
            sql.append(" and (");
            sql.append(keywordColumns.stream().map(item -> item + " like concat('%', #{keyword}, '%')").collect(Collectors.joining(" or ")));
            sql.append(")");
        }
        sql.append(" order by ").append(params.get("orderBy"));
        return sql.toString();
    }

    public String selectById(Map<String, Object> params) {
        return "select " + params.get("columns") + " from " + params.get("table") + " where id = #{id}";
    }

    public String insert(Map<String, Object> params) {
        Map<String, Object> data = (Map<String, Object>) params.get("data");
        List<String> keys = data.keySet().stream()
                .filter(key -> !"id".equals(key))
                .filter(key -> data.get(key) != null)
                .collect(Collectors.toList());
        String columns = keys.stream().map(this::camelToSnake).collect(Collectors.joining(", "));
        String values = keys.stream().map(key -> "#{data." + key + "}").collect(Collectors.joining(", "));
        return "insert into " + params.get("table") + " (" + columns + ") values (" + values + ")";
    }

    public String update(Map<String, Object> params) {
        Map<String, Object> data = (Map<String, Object>) params.get("data");
        List<String> keys = data.keySet().stream()
                .filter(key -> !"id".equals(key))
                .filter(key -> data.get(key) != null)
                .collect(Collectors.toList());
        String sets = keys.stream().map(key -> camelToSnake(key) + " = #{data." + key + "}").collect(Collectors.joining(", "));
        return "update " + params.get("table") + " set " + sets + " where id = #{data.id}";
    }

    public String delete(Map<String, Object> params) {
        return "delete from " + params.get("table") + " where id = #{id}";
    }

    public String updateField(Map<String, Object> params) {
        return "update " + params.get("table") + " set " + camelToSnake(String.valueOf(params.get("field"))) + " = #{value} where id = #{id}";
    }

    public String countAll(Map<String, Object> params) {
        return "select count(1) from " + params.get("table");
    }

    public String countWhere(Map<String, Object> params) {
        return "select count(1) from " + params.get("table") + " where " + camelToSnake(String.valueOf(params.get("field"))) + " = #{value}";
    }

    private void appendFilters(StringBuilder sql, Map<String, Object> params) {
        Map<String, Object> filters = (Map<String, Object>) params.get("filters");
        if (filters == null || filters.isEmpty()) {
            return;
        }
        filters.forEach((key, value) -> {
            if (value != null && !"".equals(String.valueOf(value))) {
                sql.append(" and ").append(camelToSnake(key)).append(" = #{filters.").append(key).append("}");
            }
        });
    }

    private String camelToSnake(String value) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (Character.isUpperCase(c)) {
                builder.append("_").append(Character.toLowerCase(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}

