package com.xiaou.dreamdonation.service;

import com.xiaou.dreamdonation.dto.DonationCreateDTO;
import com.xiaou.dreamdonation.entity.Donation;
import com.xiaou.dreamdonation.entity.DonationProject;
import com.xiaou.dreamdonation.entity.User;
import com.xiaou.dreamdonation.repository.DonationProjectRepository;
import com.xiaou.dreamdonation.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final DonationProjectRepository projectRepository;
    private final UserService userService;

    @Transactional
    public Donation createDonation(DonationCreateDTO dto, Long userId) {
        User user = userService.getUserById(userId);
        DonationProject project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -&gt; new RuntimeException("项目不存在"));

        if (project.getStatus() != DonationProject.ProjectStatus.ACTIVE) {
            throw new RuntimeException("项目未激活，无法捐赠");
        }

        Donation donation = new Donation();
        donation.setUser(user);
        donation.setProject(project);
        donation.setAmount(dto.getAmount());
        donation.setMessage(dto.getMessage());
        donation.setAnonymous(dto.getAnonymous());
        donation.setPaymentMethod(dto.getPaymentMethod());
        donation.setTransactionId(UUID.randomUUID().toString());
        donation.setPaymentStatus(Donation.PaymentStatus.SUCCESS);
        donation.setPaymentTime(LocalDateTime.now());

        donation = donationRepository.save(donation);

        project.setCurrentAmount(project.getCurrentAmount().add(dto.getAmount()));
        project.setDonorCount(project.getDonorCount() + 1);
        projectRepository.save(project);

        return donation;
    }

    public Page&lt;Donation&gt; getMyDonations(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        return donationRepository.findByUserId(userId, pageable);
    }

    public Page&lt;Donation&gt; getProjectDonations(Long projectId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        return donationRepository.findByProjectId(projectId, pageable);
    }

    public BigDecimal getProjectTotalAmount(Long projectId) {
        BigDecimal total = donationRepository.sumAmountByProjectId(projectId);
        return total != null ? total : BigDecimal.ZERO;
    }

    public Long getProjectDonorCount(Long projectId) {
        Long count = donationRepository.countDistinctDonorsByProjectId(projectId);
        return count != null ? count : 0L;
    }
}
