package com.xiaou.artist.mapper;

import com.xiaou.artist.entity.Artist;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ArtistMapper {
    Artist selectById(Long id);
    Artist selectByUserId(Long userId);
    List<Artist> selectAll();
    List<Artist> selectByStatus(String status);
    int insert(Artist artist);
    int update(Artist artist);
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    int updateRating(@Param("id") Long id, @Param("rating") java.math.BigDecimal rating);
    int incrementOrderCount(Long id);
}
