package com.xiaou.artist.mapper;

import com.xiaou.artist.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface OrderMapper {
    Order selectById(Long id);
    Order selectByOrderNo(String orderNo);
    List<Order> selectByUserId(Long userId);
    List<Order> selectByArtistId(Long artistId);
    List<Order> selectAll();
    int insert(Order order);
    int update(Order order);
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    int updateDraftUrl(@Param("id") Long id, @Param("url") String url);
    int updateFinalUrl(@Param("id") Long id, @Param("url") String url);
    int incrementReviseCount(Long id);
}
