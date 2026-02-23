package com.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.mapper.CommentMapper;
import com.blog.mapper.PostMapper;
import com.blog.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PostMapper postMapper;

    @Resource
    private CommentMapper commentMapper;

    public Map<String, Object> stats() {
        Map<String, Object> map = new HashMap<>();
        map.put("userCount", userMapper.selectCount(new LambdaQueryWrapper<User>()));
        map.put("postCount", postMapper.selectCount(new LambdaQueryWrapper<Post>()));
        map.put("publishedPostCount", postMapper.selectCount(new LambdaQueryWrapper<Post>().eq(Post::getStatus, 1)));
        map.put("commentCount", commentMapper.selectCount(new LambdaQueryWrapper<Comment>()));
        map.put("pendingCommentCount", commentMapper.selectCount(new LambdaQueryWrapper<Comment>().eq(Comment::getStatus, 0)));
        return map;
    }

    public Map<String, Object> trend() {
        LocalDate startDay = LocalDate.now().minusDays(6);
        LocalDateTime start = startDay.atStartOfDay();
        LocalDateTime end = LocalDate.now().plusDays(1).atStartOfDay();
        List<Map<String, Object>> list = postMapper.dailyCreatedCount(start, end);
        Map<String, Long> dayMap = new HashMap<>();
        for (Map<String, Object> row : list) {
            dayMap.put(row.get("day").toString(), Long.parseLong(row.get("total").toString()));
        }
        List<Map<String, Object>> daily = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {
            String day = LocalDate.now().minusDays(i).toString();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("day", day);
            item.put("postCount", dayMap.getOrDefault(day, 0L));
            daily.add(item);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("daily", daily);
        return result;
    }
}
