package com.xiaou.herbal.controller;

import com.xiaou.herbal.common.Result;
import com.xiaou.herbal.entity.Topic;
import com.xiaou.herbal.service.TopicService;
import com.xiaou.herbal.util.UserContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
@Validated
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping("/create")
    public Result<Void> createTopic(@RequestBody Topic topic) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            topicService.createTopic(userId, topic);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<Topic>> listTopics() {
        try {
            List<Topic> topics = topicService.listPublishedTopics();
            return Result.success(topics);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/{id}/detail")
    public Result<Topic> getTopicDetail(@PathVariable Long id) {
        try {
            Topic topic = topicService.getTopicDetail(id);
            return Result.success(topic);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/search")
    public Result<List<Topic>> searchTopics(@RequestParam String keyword) {
        try {
            List<Topic> topics = topicService.searchTopics(keyword);
            return Result.success(topics);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Result<Void> updateTopic(
            @PathVariable Long id,
            @RequestBody Topic topic) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            topicService.updateTopic(id, topic);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteTopic(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            topicService.deleteTopic(id);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/my-topics")
    public Result<List<Topic>> getMyTopics() {
        Long userId = UserContext.getUserId();
        if (userId == null) {
            return Result.error(401, "未授权");
        }
        try {
            List<Topic> topics = topicService.getUserTopics(userId);
            return Result.success(topics);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
