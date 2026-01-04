package com.xiaou.dreamdonation.repository;

import com.xiaou.dreamdonation.entity.DonationProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DonationProjectRepository extends JpaRepository&lt;DonationProject, Long&gt; {
    Page&lt;DonationProject&gt; findByStatus(DonationProject.ProjectStatus status, Pageable pageable);
    Page&lt;DonationProject&gt; findByCategory(String category, Pageable pageable);
    Page&lt;DonationProject&gt; findByCreatorId(Long creatorId, Pageable pageable);
    
    @Query("SELECT DISTINCT p.category FROM DonationProject p WHERE p.category IS NOT NULL")
    List&lt;String&gt; findAllCategories();
    
    @Query("SELECT p FROM DonationProject p WHERE p.status = 'ACTIVE' ORDER BY p.createTime DESC")
    List&lt;DonationProject&gt; findLatestActiveProjects(Pageable pageable);
}
