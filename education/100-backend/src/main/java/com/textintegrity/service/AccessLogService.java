package com.textintegrity.service;

import com.textintegrity.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccessLogService {

    @Autowired
    private CommonMapper commonMapper;

    public void record(Long userId, String moduleName, String actionType, String description) {
        Map<String, Object> user = userId == null ? null : commonMapper.findUserById(userId);
        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("username", user == null ? "system" : user.get("username"));
        data.put("role", user == null ? "SYSTEM" : user.get("role"));
        data.put("moduleName", moduleName);
        data.put("actionType", actionType);
        data.put("description", description);
        data.put("createTime", LocalDateTime.now());
        commonMapper.insert("operation_log", data);
    }
}

