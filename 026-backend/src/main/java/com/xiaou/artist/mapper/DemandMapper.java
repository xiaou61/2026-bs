package com.xiaou.artist.mapper;

import com.xiaou.artist.entity.Demand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface DemandMapper {
    Demand selectById(Long id);
    List<Demand> selectByUserId(Long userId);
    List<Demand> selectAll();
    List<Demand> selectOpenDemands();
    int insert(Demand demand);
    int update(Demand demand);
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    int deleteById(Long id);
}
