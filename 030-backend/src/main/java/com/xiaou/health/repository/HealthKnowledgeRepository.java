package com.xiaou.health.repository;

import com.xiaou.health.entity.HealthKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HealthKnowledgeRepository extends JpaRepository<HealthKnowledge, Long> {
    List<HealthKnowledge> findByStatusOrderByCreateTimeDesc(Integer status);
    List<HealthKnowledge> findByCategoryAndStatus(String category, Integer status);
}
