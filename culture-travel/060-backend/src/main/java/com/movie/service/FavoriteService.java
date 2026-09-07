package com.movie.service;

import com.movie.entity.Favorite;
import com.movie.mapper.FavoriteMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FavoriteService {

    @Resource
    private FavoriteMapper favoriteMapper;

    public void toggle(Long userId, Long movieId) {
        Favorite exist = favoriteMapper.selectByUserAndMovie(userId, movieId);
        if (exist != null) {
            favoriteMapper.deleteByUserAndMovie(userId, movieId);
        } else {
            Favorite favorite = new Favorite();
            favorite.setUserId(userId);
            favorite.setMovieId(movieId);
            favoriteMapper.insert(favorite);
        }
    }

    public List<Favorite> getMyFavorites(Long userId) {
        return favoriteMapper.selectByUserId(userId);
    }

    public boolean isFavorite(Long userId, Long movieId) {
        return favoriteMapper.selectByUserAndMovie(userId, movieId) != null;
    }
}
