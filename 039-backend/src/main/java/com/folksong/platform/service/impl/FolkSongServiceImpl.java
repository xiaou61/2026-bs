package com.folksong.platform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.folksong.platform.common.PageResult;
import com.folksong.platform.dto.FolkSongDTO;
import com.folksong.platform.entity.*;
import com.folksong.platform.exception.BusinessException;
import com.folksong.platform.repository.*;
import com.folksong.platform.service.FolkSongService;
import com.folksong.platform.vo.FolkSongVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FolkSongServiceImpl implements FolkSongService {

    private final FolkSongRepository folkSongRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public PageResult<FolkSongVO> getApprovedSongs(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<FolkSong> songs = folkSongRepository.findApprovedWithPage(pageSize, offset);
        Long total = folkSongRepository.countApproved();
        List<FolkSongVO> voList = songs.stream().map(s -> convertToVO(s, null)).collect(Collectors.toList());
        return PageResult.of(voList, total, pageNum, pageSize);
    }

    @Override
    public PageResult<FolkSongVO> getSongsByCategory(Long categoryId, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<FolkSong> songs = folkSongRepository.findByCategoryIdWithPage(categoryId, pageSize, offset);
        Long total = folkSongRepository.countByCategoryId(categoryId);
        List<FolkSongVO> voList = songs.stream().map(s -> convertToVO(s, null)).collect(Collectors.toList());
        return PageResult.of(voList, total, pageNum, pageSize);
    }

    @Override
    public PageResult<FolkSongVO> getUserSongs(Long userId, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<FolkSong> songs = folkSongRepository.findByUserIdWithPage(userId, pageSize, offset);
        Long total = folkSongRepository.countByUserId(userId);
        List<FolkSongVO> voList = songs.stream().map(s -> convertToVO(s, userId)).collect(Collectors.toList());
        return PageResult.of(voList, total, pageNum, pageSize);
    }

    @Override
    public PageResult<FolkSongVO> searchSongs(String keyword, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        String pattern = "%" + keyword + "%";
        List<FolkSong> songs = folkSongRepository.searchByKeyword(pattern, pageSize, offset);
        Long total = folkSongRepository.countByKeyword(pattern);
        List<FolkSongVO> voList = songs.stream().map(s -> convertToVO(s, null)).collect(Collectors.toList());
        return PageResult.of(voList, total, pageNum, pageSize);
    }

    @Override
    @Transactional
    public FolkSongVO getSongDetail(Long id, Long currentUserId) {
        FolkSong song = folkSongRepository.findById(id)
                .orElseThrow(() -> new BusinessException("民歌不存在"));
        folkSongRepository.incrementViewCount(id);
        song.setViewCount(song.getViewCount() + 1);
        return convertToVO(song, currentUserId);
    }

    @Override
    public void createSong(Long userId, FolkSongDTO dto) {
        FolkSong song = new FolkSong();
        BeanUtil.copyProperties(dto, song);
        song.setUserId(userId);
        song.setViewCount(0);
        song.setLikeCount(0);
        song.setCollectCount(0);
        song.setCommentCount(0);
        song.setStatus(1);
        song.setAuditStatus(0);
        song.setCreateTime(LocalDateTime.now());
        song.setUpdateTime(LocalDateTime.now());
        folkSongRepository.save(song);
    }

    @Override
    public void updateSong(Long userId, FolkSongDTO dto) {
        FolkSong song = folkSongRepository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException("民歌不存在"));
        if (!song.getUserId().equals(userId)) {
            throw new BusinessException("无权修改他人作品");
        }
        if (dto.getTitle() != null) song.setTitle(dto.getTitle());
        if (dto.getCategoryId() != null) song.setCategoryId(dto.getCategoryId());
        if (dto.getContent() != null) song.setContent(dto.getContent());
        if (dto.getLyrics() != null) song.setLyrics(dto.getLyrics());
        if (dto.getAudioUrl() != null) song.setAudioUrl(dto.getAudioUrl());
        if (dto.getVideoUrl() != null) song.setVideoUrl(dto.getVideoUrl());
        if (dto.getCoverImage() != null) song.setCoverImage(dto.getCoverImage());
        if (dto.getRegion() != null) song.setRegion(dto.getRegion());
        if (dto.getEthnic() != null) song.setEthnic(dto.getEthnic());
        if (dto.getIntroduction() != null) song.setIntroduction(dto.getIntroduction());
        song.setUpdateTime(LocalDateTime.now());
        folkSongRepository.save(song);
    }

    @Override
    public void deleteSong(Long userId, Long id) {
        FolkSong song = folkSongRepository.findById(id)
                .orElseThrow(() -> new BusinessException("民歌不存在"));
        if (!song.getUserId().equals(userId)) {
            throw new BusinessException("无权删除他人作品");
        }
        folkSongRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void likeSong(Long userId, Long songId) {
        if (likeRepository.existsByUserIdAndSongId(userId, songId)) {
            throw new BusinessException("已经点赞过了");
        }
        Like like = new Like();
        like.setUserId(userId);
        like.setSongId(songId);
        like.setCreateTime(LocalDateTime.now());
        likeRepository.save(like);
        folkSongRepository.updateLikeCount(songId, 1);
    }

    @Override
    @Transactional
    public void unlikeSong(Long userId, Long songId) {
        likeRepository.deleteByUserIdAndSongId(userId, songId);
        folkSongRepository.updateLikeCount(songId, -1);
    }

    @Override
    @Transactional
    public void collectSong(Long userId, Long songId) {
        if (favoriteRepository.existsByUserIdAndSongId(userId, songId)) {
            throw new BusinessException("已经收藏过了");
        }
        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setSongId(songId);
        favorite.setCreateTime(LocalDateTime.now());
        favoriteRepository.save(favorite);
        folkSongRepository.updateCollectCount(songId, 1);
    }

    @Override
    @Transactional
    public void uncollectSong(Long userId, Long songId) {
        favoriteRepository.deleteByUserIdAndSongId(userId, songId);
        folkSongRepository.updateCollectCount(songId, -1);
    }

    @Override
    public List<FolkSongVO> getHotSongs(Integer limit) {
        return folkSongRepository.findHot(limit).stream()
                .map(s -> convertToVO(s, null)).collect(Collectors.toList());
    }

    @Override
    public List<FolkSongVO> getLatestSongs(Integer limit) {
        return folkSongRepository.findLatest(limit).stream()
                .map(s -> convertToVO(s, null)).collect(Collectors.toList());
    }

    @Override
    public PageResult<FolkSongVO> getUserFavorites(Long userId, Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<Favorite> favorites = favoriteRepository.findByUserIdWithPage(userId, pageSize, offset);
        Long total = favoriteRepository.countByUserId(userId);
        List<FolkSongVO> voList = favorites.stream()
                .map(f -> folkSongRepository.findById(f.getSongId()).orElse(null))
                .filter(s -> s != null)
                .map(s -> convertToVO(s, userId))
                .collect(Collectors.toList());
        return PageResult.of(voList, total, pageNum, pageSize);
    }

    @Override
    public PageResult<FolkSongVO> getPendingSongs(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<FolkSong> songs = folkSongRepository.findByAuditStatusWithPage(0, pageSize, offset);
        Long total = folkSongRepository.countByAuditStatus(0);
        List<FolkSongVO> voList = songs.stream().map(s -> convertToVO(s, null)).collect(Collectors.toList());
        return PageResult.of(voList, total, pageNum, pageSize);
    }

    @Override
    public PageResult<FolkSongVO> getAllSongs(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<FolkSong> songs = folkSongRepository.findAllWithPage(pageSize, offset);
        Long total = folkSongRepository.count();
        List<FolkSongVO> voList = songs.stream().map(s -> convertToVO(s, null)).collect(Collectors.toList());
        return PageResult.of(voList, total, pageNum, pageSize);
    }

    @Override
    public void auditSong(Long songId, Integer auditStatus) {
        folkSongRepository.updateAuditStatus(songId, auditStatus);
    }

    private FolkSongVO convertToVO(FolkSong song, Long currentUserId) {
        FolkSongVO vo = new FolkSongVO();
        BeanUtil.copyProperties(song, vo);
        categoryRepository.findById(song.getCategoryId()).ifPresent(c -> vo.setCategoryName(c.getName()));
        userRepository.findById(song.getUserId()).ifPresent(u -> {
            vo.setUserName(u.getNickname());
            vo.setUserAvatar(u.getAvatar());
        });
        if (currentUserId != null) {
            vo.setIsLiked(likeRepository.existsByUserIdAndSongId(currentUserId, song.getId()));
            vo.setIsCollected(favoriteRepository.existsByUserIdAndSongId(currentUserId, song.getId()));
        } else {
            vo.setIsLiked(false);
            vo.setIsCollected(false);
        }
        return vo;
    }
}
