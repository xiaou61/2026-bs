package com.xiaou.artist.service;

import com.xiaou.artist.entity.Artist;
import java.util.List;

public interface ArtistService {
    Artist applyArtist(Artist artist);
    List<Artist> getPendingArtists();
    boolean approveArtist(Long id);
    boolean rejectArtist(Long id);
    Artist getArtistById(Long id);
    Artist getArtistByUserId(Long userId);
    List<Artist> getAllArtists();
    List<Artist> getApprovedArtists();
    boolean updateArtist(Artist artist);
}
