package com.xiaou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.entity.LeaveRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请假申请Mapper接口
 * @author xiaou
 */
@Mapper
public interface LeaveRequestMapper extends BaseMapper<LeaveRequest> {
}

