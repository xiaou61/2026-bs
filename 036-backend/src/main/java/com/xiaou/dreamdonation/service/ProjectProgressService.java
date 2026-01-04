package com.xiaou.dreamdonation.service;

import com.xiaou.dreamdonation.dto.ProgressCreateDTO;
import com.xiaou.dreamdonation.entity.DonationProject;
import com.xiaou.dreamdonation.entity.ProjectProgress;
import com.xiaou.dreamdonation.repository.ProjectProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectProgressService {
    private final ProjectProgressRepository progressRepository;
    private final DonationProjectService projectService;

    @Transactional
    public ProjectProgress createProgress(ProgressCreateDTO dto, Long userId) {
        DonationProject project = projectService.getProjectById(dto.getProjectId());

        if (!project.getCreator().getId().equals(userId)) {
            throw new RuntimeException("无权发布此项目的进度");
        }

        ProjectProgress progress = new ProjectProgress();
        progress.setProject(project);
        progress.setTitle(dto.getTitle());
        progress.setContent(dto.getContent());
        progress.setImages(dto.getImages());

        return progressRepository.save(progress);
    }

    public List&lt;ProjectProgress&gt; getProjectProgress(Long projectId) {
        return progressRepository.findByProjectIdOrderByCreateTimeDesc(projectId);
    }

    @Transactional
    public void deleteProgress(Long progressId, Long userId) {
        ProjectProgress progress = progressRepository.findById(progressId)
                .orElseThrow(() -&gt; new RuntimeException("进度记录不存在"));

        if (!progress.getProject().getCreator().getId().equals(userId)) {
            throw new RuntimeException("无权删除此进度记录");
        }

        progressRepository.deleteById(progressId);
    }
}
