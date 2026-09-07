package com.xiaou.dreamdonation.service;

import com.xiaou.dreamdonation.common.BusinessException;
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
        if (creator.getRole() != User.UserRole.ADMIN && creator.getRole() != User.UserRole.ORGANIZATION) {
            throw BusinessException.forbidden("只有组织账号或管理员可以创建项目");
        }
        validateProjectDates(dto);

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

    public Page<DonationProject> listProjects(int page, int size, String category, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));

        if (category != null && !category.isEmpty()) {
            return projectRepository.findByCategory(category, pageable);
        }

        if (status != null && !status.isEmpty()) {
            DonationProject.ProjectStatus projectStatus = DonationProject.ProjectStatus.valueOf(status);
            return projectRepository.findByStatus(projectStatus, pageable);
        }

        return projectRepository.findAll(pageable);
    }

    public DonationProject getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> BusinessException.badRequest("项目不存在"));
    }

    public List<String> getAllCategories() {
        return projectRepository.findAllCategories();
    }

    public List<DonationProject> getLatestActiveProjects(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return projectRepository.findLatestActiveProjects(pageable);
    }

    @Transactional
    public DonationProject updateProjectStatus(Long projectId, DonationProject.ProjectStatus status, Long userId) {
        DonationProject project = getProjectById(projectId);
        ensureProjectOwnerOrAdmin(project, userId);
        project.setStatus(status);
        return projectRepository.save(project);
    }

    public Page<DonationProject> getMyProjects(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));
        return projectRepository.findByCreatorId(userId, pageable);
    }

    @Transactional
    public void deleteProject(Long projectId, Long userId) {
        DonationProject project = getProjectById(projectId);
        ensureProjectOwnerOrAdmin(project, userId);
        projectRepository.deleteById(projectId);
    }

    public void ensureProjectOwnerOrAdmin(DonationProject project, Long userId) {
        User user = userService.getUserById(userId);
        if (user.getRole() == User.UserRole.ADMIN) {
            return;
        }
        if (project.getCreator() != null && project.getCreator().getId().equals(userId)) {
            return;
        }
        throw BusinessException.forbidden("无权操作此项目");
    }

    private void validateProjectDates(ProjectCreateDTO dto) {
        if (!dto.getEndDate().isAfter(dto.getStartDate())) {
            throw BusinessException.badRequest("结束时间必须晚于开始时间");
        }
    }
}
