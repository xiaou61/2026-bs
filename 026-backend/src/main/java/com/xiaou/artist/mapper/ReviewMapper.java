package com.xiaou.artist.mapper;

import com.xiaou.artist.entity.Review;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReviewMapper {
    Review selectById(Long id);
    List<Review> selectByOrderId(Long orderId);
    List<Review> selectByToUserId(Long toUserId);
    int insert(Review review);
}
