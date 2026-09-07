package com.inventory.mapper;

import com.inventory.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("<script>" +
            "select * from category where 1=1 " +
            "<if test='name!=null and name!=\"\"'>and name like concat('%',#{name},'%')</if> " +
            "<if test='status!=null'>and status=#{status}</if> " +
            "order by sort asc,id desc" +
            "</script>")
    List<Category> selectPageList(@Param("name") String name, @Param("status") Integer status);

    @Select("select * from category where id=#{id}")
    Category selectById(@Param("id") Long id);

    @Select("select * from category where status=1 order by sort asc,id desc")
    List<Category> selectEnabledList();

    @Insert("insert into category(name,sort,status) values(#{name},#{sort},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);

    @Update("update category set name=#{name},sort=#{sort},status=#{status} where id=#{id}")
    int update(Category category);

    @Delete("delete from category where id=#{id}")
    int deleteById(@Param("id") Long id);
}
