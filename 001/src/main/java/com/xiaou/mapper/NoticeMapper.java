package com.xiaou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公告Mapper接口
 * @author xiaou
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
}

