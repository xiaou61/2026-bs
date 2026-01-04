package com.xiaou.dreamdonation.repository;

import com.xiaou.dreamdonation.entity.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository&lt;Donation, Long&gt; {
    Page&lt;Donation&gt; findByUserId(Long userId, Pageable pageable);
    Page&lt;Donation&gt; findByProjectId(Long projectId, Pageable pageable);
    List&lt;Donation&gt; findByProjectIdAndPaymentStatus(Long projectId, Donation.PaymentStatus status);
    
    @Query("SELECT SUM(d.amount) FROM Donation d WHERE d.project.id = :projectId AND d.paymentStatus = 'SUCCESS'")
    BigDecimal sumAmountByProjectId(Long projectId);
    
    @Query("SELECT COUNT(DISTINCT d.user.id) FROM Donation d WHERE d.project.id = :projectId AND d.paymentStatus = 'SUCCESS'")
    Long countDistinctDonorsByProjectId(Long projectId);
}
