package com.xiaou.wedding.mapper;

import com.xiaou.wedding.entity.Photo;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PhotoMapper {
    List<Photo> listByOrder(@Param("orderId") Long orderId);

    int insert(Photo photo);

    int update(Photo photo);
}
