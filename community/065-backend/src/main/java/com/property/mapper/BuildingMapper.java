package com.property.mapper;

import com.property.entity.Building;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BuildingMapper {

    @Select("<script>" +
            "select * from building where 1=1 " +
            "<if test='name!=null and name!=\"\"'>and name like concat('%',#{name},'%')</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by id desc" +
            "</script>")
    List<Building> selectPageList(@Param("name") String name, @Param("status") Integer status);

    @Select("select * from building where status=1 order by id desc")
    List<Building> selectEnabledList();

    @Select("select * from building where id=#{id}")
    Building selectById(@Param("id") Long id);

    @Insert("insert into building(name,floors,status) values(#{name},#{floors},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Building building);

    @Update("update building set name=#{name},floors=#{floors},status=#{status} where id=#{id}")
    int update(Building building);

    @Delete("delete from building where id=#{id}")
    int deleteById(@Param("id") Long id);
}
