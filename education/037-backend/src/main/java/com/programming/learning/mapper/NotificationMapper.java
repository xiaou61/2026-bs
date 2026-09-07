package com.programming.learning.mapper;

import com.programming.learning.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知Mapper接口
 */
@Mapper
public interface NotificationMapper {

    int insert(Notification notification);

    int deleteById(Long id);

    Notification selectById(Long id);

    List<Notification> selectByUserId(@Param("userId") Long userId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    Long countByUserId(Long userId);

    Long countUnreadByUserId(Long userId);

    int markAsRead(Long id);

    int markAllAsRead(Long userId);
}
