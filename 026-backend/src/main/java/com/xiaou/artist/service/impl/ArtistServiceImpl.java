package com.xiaou.artist.service.impl;

import com.xiaou.artist.entity.Artist;
import com.xiaou.artist.mapper.ArtistMapper;
import com.xiaou.artist.mapper.UserMapper;
import com.xiaou.artist.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {
    
    @Autowired
    private ArtistMapper artistMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public Artist applyArtist(Artist artist) {
        Artist existArtist = artistMapper.selectByUserId(artist.getUserId());
        if (existArtist != null) {
            throw new RuntimeException("您已提交过画师认证申请");
        }
        if (artist.getStatus() == null) {
            artist.setStatus("PENDING");
        }
        artistMapper.insert(artist);
        return artist;
    }
    
    @Override
    public List<Artist> getPendingArtists() {
        return artistMapper.selectByStatus("PENDING");
    }
    
    @Override
    public boolean approveArtist(Long id) {
        Artist artist = artistMapper.selectById(id);
        if (artist == null) {
            throw new RuntimeException("画师不存在");
        }
        // 更新画师状态
        artistMapper.updateStatus(id, "APPROVED");
        // 更新用户角色为ARTIST
        var user = userMapper.selectById(artist.getUserId());
        user.setRole("ARTIST");
        userMapper.update(user);
        return true;
    }
    
    @Override
    public boolean rejectArtist(Long id) {
        return artistMapper.updateStatus(id, "REJECTED") > 0;
    }
    
    @Override
    public Artist getArtistById(Long id) {
        return artistMapper.selectById(id);
    }
    
    @Override
    public Artist getArtistByUserId(Long userId) {
        return artistMapper.selectByUserId(userId);
    }
    
    @Override
    public List<Artist> getAllArtists() {
        return artistMapper.selectAll();
    }
    
    @Override
    public List<Artist> getApprovedArtists() {
        return artistMapper.selectByStatus("APPROVED");
    }
    
    @Override
    public boolean updateArtist(Artist artist) {
        return artistMapper.update(artist) > 0;
    }
}
