package com.xiaou.bike.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaou.bike.entity.Bicycle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 自行车Mapper接口
 */
@Mapper
public interface BicycleMapper extends BaseMapper<Bicycle> {

    /**
     * 更新车辆状态
     */
    @Update("UPDATE bicycle SET status = #{status}, station_id = #{stationId} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("stationId") Long stationId);
}
