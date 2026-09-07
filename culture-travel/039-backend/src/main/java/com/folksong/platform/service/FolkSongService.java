package com.folksong.platform.service;

import com.folksong.platform.common.PageResult;
import com.folksong.platform.dto.FolkSongDTO;
import com.folksong.platform.vo.FolkSongVO;
import java.util.List;

public interface FolkSongService {
    PageResult<FolkSongVO> getApprovedSongs(Integer pageNum, Integer pageSize);
    PageResult<FolkSongVO> getSongsByCategory(Long categoryId, Integer pageNum, Integer pageSize);
    PageResult<FolkSongVO> getUserSongs(Long userId, Integer pageNum, Integer pageSize);
    PageResult<FolkSongVO> searchSongs(String keyword, Integer pageNum, Integer pageSize);
    FolkSongVO getSongDetail(Long id, Long currentUserId);
    void createSong(Long userId, FolkSongDTO dto);
    void updateSong(Long userId, FolkSongDTO dto);
    void deleteSong(Long userId, Long id);
    void likeSong(Long userId, Long songId);
    void unlikeSong(Long userId, Long songId);
    void collectSong(Long userId, Long songId);
    void uncollectSong(Long userId, Long songId);
    List<FolkSongVO> getHotSongs(Integer limit);
    List<FolkSongVO> getLatestSongs(Integer limit);
    PageResult<FolkSongVO> getUserFavorites(Long userId, Integer pageNum, Integer pageSize);
    PageResult<FolkSongVO> getPendingSongs(Integer pageNum, Integer pageSize);
    PageResult<FolkSongVO> getAllSongs(Integer pageNum, Integer pageSize);
    void auditSong(Long songId, Integer auditStatus);
}
