package com.folksong.platform.service;

import com.folksong.platform.common.PageResult;
import com.folksong.platform.dto.AnnouncementDTO;
import com.folksong.platform.entity.Announcement;
import java.util.List;

public interface AnnouncementService {
    PageResult<Announcement> getActiveAnnouncements(Integer pageNum, Integer pageSize);
    List<Announcement> getLatestAnnouncements(Integer limit);
    Announcement getAnnouncementById(Long id);
    PageResult<Announcement> getAllAnnouncements(Integer pageNum, Integer pageSize);
    void createAnnouncement(Long publisherId, AnnouncementDTO dto);
    void updateAnnouncement(AnnouncementDTO dto);
    void deleteAnnouncement(Long id);
}
