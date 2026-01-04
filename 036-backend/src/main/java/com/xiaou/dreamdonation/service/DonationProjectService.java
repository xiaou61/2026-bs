package com.xiaou.dreamdonation.service;

import com.xiaou.dreamdonation.dto.ProjectCreateDTO;
import com.xiaou.dreamdonation.entity.DonationProject;
import com.xiaou.dreamdonation.entity.User;
import com.xiaou.dreamdonation.repository.DonationProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationProjectService {
    private final DonationProjectRepository projectRepository;
    private final UserService userService;

    @Transactional
    public DonationProject createProject(ProjectCreateDTO dto, Long userId) {
        User creator = userService.getUserById(userId);

        DonationProject project = new DonationProject();
        project.setTitle(dto.getTitle());
        project.setDescription(dto.getDescription());
        project.setCoverImage(dto.getCoverImage());
        project.setCategory(dto.getCategory());
        project.setTargetAmount(dto.getTargetAmount());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setOrganizationName(dto.getOrganizationName());
        project.setLocation(dto.getLocation());
        project.setCreator(creator);

        return projectRepository.save(project);
    }

    public Page&lt;DonationProject&gt; listProjects(int page, int size, String category, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));

        if (category != null &amp;&amp; !category.isEmpty()) {
            return projectRepository.findByCategory(category, pageable);
        }

        if (status != null &amp;&amp; !status.isEmpty()) {
            DonationProject.ProjectStatus projectStatus = DonationProject.ProjectStatus.valueOf(status);
            return projectRepository.findByStatus(projectStatus, pageable);
        }

        return projectRepository.findAll(pageable);
    }

    public DonationProject getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -&gt; new RuntimeException("项目不存在"));
    }

    public List&lt;String&gt; getAllCategories() {
        return projectRepository.findAllCategories();
    }

    public List&lt;DonationProject&gt; getLatestActiveProjects(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return projectRepository.findLatestActiveProjects(pageable);
    }

    @Transactional
    public DonationProject updateProjectStatus(Long projectId, DonationProject.ProjectStatus status) {
        DonationProject project = getProjectById(projectId);
        project.setStatus(status);
        return projectRepository.save(project);
    }

    public Page&lt;DonationProject&gt; getMyProjects(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        return projectRepository.findByCreatorId(userId, pageable);
    }

    @Transactional
    public void deleteProject(Long projectId, Long userId) {
        DonationProject project = getProjectById(projectId);
        if (!project.getCreator().getId().equals(userId)) {
            throw new RuntimeException("无权删除此项目");
        }
        projectRepository.deleteById(projectId);
    }
}
