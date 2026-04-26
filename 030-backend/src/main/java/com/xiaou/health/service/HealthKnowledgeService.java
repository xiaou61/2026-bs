package com.xiaou.health.service;

import com.xiaou.health.entity.HealthKnowledge;
import com.xiaou.health.repository.HealthKnowledgeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class HealthKnowledgeService {
    private final HealthKnowledgeRepository healthKnowledgeRepository;

    public HealthKnowledgeService(HealthKnowledgeRepository healthKnowledgeRepository) {
        this.healthKnowledgeRepository = healthKnowledgeRepository;
    }

    @Transactional(readOnly = true)
    public List<HealthKnowledge> getPublishedKnowledge(String category) {
        if (StringUtils.hasText(category)) {
            return healthKnowledgeRepository.findByCategoryAndStatus(category, 1);
        }
        return healthKnowledgeRepository.findByStatusOrderByCreateTimeDesc(1);
    }

    @Transactional(readOnly = true)
    public List<HealthKnowledge> getAllKnowledge() {
        return healthKnowledgeRepository.findAll(Sort.by(Sort.Direction.DESC, "createTime"));
    }

    @Transactional
    public HealthKnowledge getKnowledgeDetail(Long id, boolean allowPreviewUnpublished) {
        HealthKnowledge knowledge = healthKnowledgeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("健康知识不存在"));

        if (!allowPreviewUnpublished && !Integer.valueOf(1).equals(knowledge.getStatus())) {
            throw new RuntimeException("健康知识不存在或未发布");
        }

        if (Integer.valueOf(1).equals(knowledge.getStatus())) {
            knowledge.setViewCount(defaultNumber(knowledge.getViewCount()) + 1);
            knowledge = healthKnowledgeRepository.save(knowledge);
        }
        return knowledge;
    }

    @Transactional
    public HealthKnowledge createKnowledge(Long authorId, HealthKnowledge request) {
        HealthKnowledge knowledge = new HealthKnowledge();
        applyEditableFields(knowledge, request);
        knowledge.setAuthorId(authorId);
        knowledge.setViewCount(defaultNumber(knowledge.getViewCount()));
        knowledge.setLikeCount(defaultNumber(knowledge.getLikeCount()));
        return healthKnowledgeRepository.save(knowledge);
    }

    @Transactional
    public HealthKnowledge updateKnowledge(Long id, HealthKnowledge request) {
        HealthKnowledge knowledge = healthKnowledgeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("健康知识不存在"));
        applyEditableFields(knowledge, request);
        return healthKnowledgeRepository.save(knowledge);
    }

    @Transactional
    public HealthKnowledge updateStatus(Long id, Integer status) {
        HealthKnowledge knowledge = healthKnowledgeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("健康知识不存在"));
        knowledge.setStatus(status);
        return healthKnowledgeRepository.save(knowledge);
    }

    @Transactional
    public void deleteKnowledge(Long id) {
        HealthKnowledge knowledge = healthKnowledgeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("健康知识不存在"));
        healthKnowledgeRepository.delete(knowledge);
    }

    private void applyEditableFields(HealthKnowledge target, HealthKnowledge source) {
        target.setCategory(source.getCategory());
        target.setTitle(source.getTitle());
        target.setSummary(source.getSummary());
        target.setContent(source.getContent());
        target.setCoverImage(source.getCoverImage());
        target.setVideoUrl(source.getVideoUrl());
        target.setStatus(source.getStatus() == null ? 1 : source.getStatus());
        if (target.getViewCount() == null) {
            target.setViewCount(0);
        }
        if (target.getLikeCount() == null) {
            target.setLikeCount(0);
        }
    }

    private int defaultNumber(Integer value) {
        return value == null ? 0 : value;
    }
}
