package com.xiaou.artist.mapper;

import com.xiaou.artist.entity.Portfolio;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PortfolioMapper {
    Portfolio selectById(Long id);
    List<Portfolio> selectByArtistId(Long artistId);
    List<Portfolio> selectAll();
    List<Portfolio> selectFeatured();
    int insert(Portfolio portfolio);
    int update(Portfolio portfolio);
    int deleteById(Long id);
    int incrementViewCount(Long id);
}
