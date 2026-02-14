package com.fraud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fraud.entity.OperationLog;
import com.fraud.entity.User;
import com.fraud.mapper.OperationLogMapper;
import com.fraud.mapper.UserMapper;
import com.fraud.vo.OperationLogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OperationLogService {

    @Resource
    private OperationLogMapper operationLogMapper;

    @Resource
    private UserMapper userMapper;

    public void add(String module, String action, Long operatorId, String operatorRole, String bizNo, String content) {
        try {
            OperationLog log = new OperationLog();
            String m = module == null ? "" : module.trim();
            String a = action == null ? "" : action.trim();
            String r = operatorRole == null ? null : operatorRole.trim();
            String b = bizNo == null ? null : bizNo.trim();
            String c = content == null ? null : content.trim();
            if (m.length() > 40) {
                m = m.substring(0, 40);
            }
            if (a.length() > 40) {
                a = a.substring(0, 40);
            }
            if (r != null && r.length() > 20) {
                r = r.substring(0, 20);
            }
            if (b != null && b.length() > 120) {
                b = b.substring(0, 120);
            }
            if (c != null && c.length() > 500) {
                c = c.substring(0, 500);
            }
            log.setModule(m);
            log.setAction(a);
            log.setOperatorId(operatorId);
            log.setOperatorRole(r);
            log.setBizNo(b);
            log.setContent(c);
            operationLogMapper.insert(log);
        } catch (Exception ignored) {
        }
    }

    public Page<OperationLogVO> page(Integer pageNum, Integer pageSize, String module, String action, Long operatorId, String keyword) {
        QueryWrapper<OperationLog> wrapper = buildWrapper(module, action, operatorId, keyword);
        Page<OperationLog> page = operationLogMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return convertPage(page);
    }

    public List<OperationLogVO> list(Integer limit, String module, String action, Long operatorId, String keyword) {
        QueryWrapper<OperationLog> wrapper = buildWrapper(module, action, operatorId, keyword);
        if (limit != null && limit > 0) {
            wrapper.last("limit " + limit);
        }
        List<OperationLog> list = operationLogMapper.selectList(wrapper);
        return convertList(list);
    }

    private QueryWrapper<OperationLog> buildWrapper(String module, String action, Long operatorId, String keyword) {
        QueryWrapper<OperationLog> wrapper = new QueryWrapper<>();
        if (module != null && !module.trim().isEmpty()) {
            wrapper.eq("module", module.trim());
        }
        if (action != null && !action.trim().isEmpty()) {
            wrapper.eq("action", action.trim());
        }
        if (operatorId != null) {
            wrapper.eq("operator_id", operatorId);
        }
        if (keyword != null && !keyword.trim().isEmpty()) {
            String key = keyword.trim();
            wrapper.and(w -> w.like("biz_no", key).or().like("content", key));
        }
        wrapper.orderByDesc("id");
        return wrapper;
    }

    private Page<OperationLogVO> convertPage(Page<OperationLog> page) {
        Page<OperationLogVO> voPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        voPage.setRecords(convertList(page.getRecords()));
        return voPage;
    }

    private List<OperationLogVO> convertList(List<OperationLog> list) {
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        Set<Long> userIds = new HashSet<>();
        for (OperationLog log : list) {
            if (log.getOperatorId() != null) {
                userIds.add(log.getOperatorId());
            }
        }
        Map<Long, User> userMap = new HashMap<>();
        if (!userIds.isEmpty()) {
            List<User> users = userMapper.selectBatchIds(userIds);
            userMap = users.stream().collect(Collectors.toMap(User::getId, item -> item));
        }
        List<OperationLogVO> result = new ArrayList<>();
        for (OperationLog log : list) {
            OperationLogVO vo = new OperationLogVO();
            BeanUtils.copyProperties(log, vo);
            User operator = userMap.get(log.getOperatorId());
            if (operator == null) {
                vo.setOperatorName("-");
            } else {
                String name = operator.getNickname();
                if (name == null || name.trim().isEmpty()) {
                    name = operator.getUsername();
                }
                vo.setOperatorName(name);
            }
            result.add(vo);
        }
        return result;
    }
}
