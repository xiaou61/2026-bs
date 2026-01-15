package com.folksong.platform.service.impl;

import com.folksong.platform.common.PageResult;
import com.folksong.platform.dto.AnnouncementDTO;
import com.folksong.platform.entity.Announcement;
import com.folksong.platform.exception.BusinessException;
import com.folksong.platform.repository.AnnouncementRepository;
import com.folksong.platform.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Override
    public PageResult<Announcement> getActiveAnnouncements(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Announcement> announcements = announcementRepository.findActiveWithPage(pageSize, offset);
        Long total = announcementRepository.countActive();
        return PageResult.of(announcements, total, pageNum, pageSize);
    }

    @Override
    public List<Announcement> getLatestAnnouncements(Integer limit) {
        return announcementRepository.findLatest(limit);
    }

    @Override
    public Announcement getAnnouncementById(Long id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new BusinessException("公告不存在"));
    }

    @Override
    public PageResult<Announcement> getAllAnnouncements(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Announcement> announcements = announcementRepository.findAllWithPage(pageSize, offset);
        Long total = announcementRepository.count();
        return PageResult.of(announcements, total, pageNum, pageSize);
    }

    @Override
    public void createAnnouncement(Long publisherId, AnnouncementDTO dto) {
        Announcement announcement = new Announcement();
        announcement.setTitle(dto.getTitle());
        announcement.setContent(dto.getContent());
        announcement.setPublisherId(publisherId);
        announcement.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        announcement.setCreateTime(LocalDateTime.now());
        announcement.setUpdateTime(LocalDateTime.now());
        announcementRepository.save(announcement);
    }

    @Override
    public void updateAnnouncement(AnnouncementDTO dto) {
        Announcement announcement = announcementRepository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException("公告不存在"));
        if (dto.getTitle() != null) announcement.setTitle(dto.getTitle());
        if (dto.getContent() != null) announcement.setContent(dto.getContent());
        if (dto.getStatus() != null) announcement.setStatus(dto.getStatus());
        announcement.setUpdateTime(LocalDateTime.now());
        announcementRepository.save(announcement);
    }

    @Override
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }
}
