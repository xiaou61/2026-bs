package com.adoption.service;

import com.adoption.entity.AdopterProfile;
import com.adoption.entity.SysUser;
import com.adoption.mapper.AdopterProfileMapper;
import com.adoption.mapper.SysUserMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdopterService {

    @Autowired
    private AdopterProfileMapper adopterProfileMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    public Map<String, Object> page(Integer pageNum, Integer pageSize) {
        Page<AdopterProfile> page = new Page<>(pageNum == null ? 1 : pageNum, pageSize == null ? 10 : pageSize);
        Page<AdopterProfile> result = adopterProfileMapper.selectPage(page, null);
        List<Long> userIds = result.getRecords().stream().map(AdopterProfile::getUserId).collect(Collectors.toList());
        List<SysUser> users = userIds.isEmpty() ? null : sysUserMapper.selectBatchIds(userIds);
        Map<Long, SysUser> userMap = new HashMap<>();
        if (users != null) {
            for (SysUser user : users) {
                userMap.put(user.getId(), user);
            }
        }
        for (AdopterProfile item : result.getRecords()) {
            SysUser user = userMap.get(item.getUserId());
            if (user != null) {
                item.setRealName(user.getRealName());
                item.setPhone(user.getPhone());
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("list", result.getRecords());
        data.put("total", result.getTotal());
        return data;
    }
}
