package com.xiaou.dreamdonation.repository;

import com.xiaou.dreamdonation.entity.ProjectProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjectProgressRepository extends JpaRepository&lt;ProjectProgress, Long&gt; {
    List&lt;ProjectProgress&gt; findByProjectIdOrderByCreateTimeDesc(Long projectId);
}
