package com.xiaou.campusvideo.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PointsMallService {
    
    public List<Map<String, Object>> getItems() {
        List<Map<String, Object>> items = new ArrayList<>();
        items.add(buildItem(1L, "创作新星勋章", 10, "虚拟勋章", "在个人主页展示专属创作勋章", "限量推荐", "适合新手创作者快速解锁"));
        items.add(buildItem(2L, "校园热爱头像框", 30, "头像装扮", "为主页头像添加活动头像框", "人气单品", "适合日常展示"));
        items.add(buildItem(3L, "推荐流加油包", 60, "曝光权益", "为新作品争取额外推荐曝光机会", "创作者常用", "适合发布新作品前兑换"));
        items.add(buildItem(4L, "校园周边马克杯", 100, "校园周边", "线下兑换纪念马克杯", "实物奖励", "需要联系管理员核销"));
        return items;
    }
    
    public Map<String, Object> getItemById(Long itemId) {
        for (Map<String, Object> item : getItems()) {
            if (((Long) item.get("id")).equals(itemId)) {
                return item;
            }
        }
        return null;
    }
    
    private Map<String, Object> buildItem(Long id, String name, Integer points, String type, String description, String tag, String note) {
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("id", id);
        item.put("name", name);
        item.put("points", points);
        item.put("type", type);
        item.put("description", description);
        item.put("tag", tag);
        item.put("note", note);
        return item;
    }
}
