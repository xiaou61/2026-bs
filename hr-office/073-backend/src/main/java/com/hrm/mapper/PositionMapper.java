package com.hrm.mapper;

import com.hrm.entity.Position;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface PositionMapper {
    Position selectById(Long id);
    List<Position> selectList(@Param("name") String name, @Param("status") Integer status);
    List<Position> selectAll();
    int insert(Position position);
    int update(Position position);
    int deleteById(Long id);
}
