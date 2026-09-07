package com.xiaou.mapper;

import com.xiaou.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface NotificationMapper {
    List<Notification> findByUserId(@Param("userId") Long userId);
    
    int countUnread(@Param("userId") Long userId);
    
    int insert(Notification notification);
    
    int updateRead(@Param("id") Long id);
    
    int updateAllRead(@Param("userId") Long userId);
    
    int deleteById(@Param("id") Long id);
}

