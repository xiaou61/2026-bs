package com.ticket.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ticket.common.BusinessException;
import com.ticket.common.PageResult;
import com.ticket.entity.Comment;
import com.ticket.entity.Movie;
import com.ticket.mapper.CommentMapper;
import com.ticket.mapper.MovieMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class MovieService {

    @Resource
    private MovieMapper movieMapper;

    @Resource
    private CommentMapper commentMapper;

    public PageResult<Movie> page(Integer pageNum, Integer pageSize, String title, String status, String category, String type) {
        Page<Movie> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<Movie>()
                .like(StringUtils.hasText(title), Movie::getTitle, title == null ? null : title.trim())
                .eq(StringUtils.hasText(status), Movie::getStatus, status == null ? null : status.trim())
                .eq(StringUtils.hasText(category), Movie::getCategory, category == null ? null : category.trim())
                .eq(StringUtils.hasText(type), Movie::getType, type == null ? null : type.trim())
                .orderByDesc(Movie::getIsRecommend)
                .orderByDesc(Movie::getId);
        Page<Movie> resultPage = movieMapper.selectPage(page, wrapper);
        PageResult<Movie> result = new PageResult<>();
        result.setTotal(resultPage.getTotal());
        result.setRecords(resultPage.getRecords());
        return result;
    }

    public PageResult<Movie> publicPage(Integer pageNum, Integer pageSize, String title, String category, String type) {
        return page(pageNum, pageSize, title, "ON", category, type);
    }

    public List<Movie> recommend(Integer limit) {
        int size = limit == null || limit <= 0 ? 8 : limit;
        return movieMapper.selectList(new LambdaQueryWrapper<Movie>()
                .eq(Movie::getStatus, "ON")
                .eq(Movie::getIsRecommend, 1)
                .orderByDesc(Movie::getRating)
                .last("limit " + size));
    }

    public Movie getById(Long id) {
        Movie movie = movieMapper.selectById(id);
        if (movie == null) {
            throw new BusinessException("影片不存在");
        }
        return movie;
    }

    public void save(Movie movie) {
        if (movie == null || !StringUtils.hasText(movie.getTitle())) {
            throw new BusinessException("影片标题不能为空");
        }
        movie.setTitle(movie.getTitle().trim());
        movie.setType(StringUtils.hasText(movie.getType()) ? movie.getType().trim() : "MOVIE");
        movie.setCategory(StringUtils.hasText(movie.getCategory()) ? movie.getCategory().trim() : "其他");
        movie.setDirector(StringUtils.hasText(movie.getDirector()) ? movie.getDirector().trim() : "");
        movie.setActors(StringUtils.hasText(movie.getActors()) ? movie.getActors().trim() : "");
        movie.setPoster(StringUtils.hasText(movie.getPoster()) ? movie.getPoster().trim() : "");
        movie.setDescription(StringUtils.hasText(movie.getDescription()) ? movie.getDescription().trim() : "");
        movie.setStatus(StringUtils.hasText(movie.getStatus()) ? movie.getStatus().trim() : "ON");
        movie.setIsRecommend(movie.getIsRecommend() == null ? 0 : movie.getIsRecommend());
        movie.setCommentCount(movie.getCommentCount() == null ? 0 : movie.getCommentCount());
        movie.setRating(movie.getRating() == null ? BigDecimal.ZERO : movie.getRating());
        if (movie.getId() == null) {
            movieMapper.insert(movie);
        } else {
            if (movieMapper.selectById(movie.getId()) == null) {
                throw new BusinessException("影片不存在");
            }
            movieMapper.updateById(movie);
        }
    }

    public void deleteById(Long id) {
        movieMapper.deleteById(id);
    }

    public Long countAll() {
        return movieMapper.selectCount(null);
    }

    public void refreshStats(Long movieId) {
        Movie movie = movieMapper.selectById(movieId);
        if (movie == null) {
            return;
        }
        List<Comment> comments = commentMapper.selectList(new LambdaQueryWrapper<Comment>()
                .eq(Comment::getMovieId, movieId)
                .eq(Comment::getStatus, "APPROVED"));
        int count = comments.size();
        BigDecimal avg = BigDecimal.ZERO;
        if (count > 0) {
            int sum = comments.stream().map(Comment::getRating).filter(v -> v != null).mapToInt(Integer::intValue).sum();
            avg = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(count), 1, RoundingMode.HALF_UP);
        }
        movie.setCommentCount(count);
        movie.setRating(avg);
        movieMapper.updateById(movie);
    }
}
