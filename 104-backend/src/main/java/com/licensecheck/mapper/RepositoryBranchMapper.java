package com.licensecheck.mapper;

import com.licensecheck.entity.RepositoryBranch;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RepositoryBranchMapper {
    @Select({
        "<script>",
        "SELECT * FROM repository_branch",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (branch_name LIKE CONCAT('%',#{keyword},'%') OR commit_hash LIKE CONCAT('%',#{keyword},'%') OR status LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<RepositoryBranch> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM repository_branch WHERE id = #{id}")
    RepositoryBranch selectById(Long id);

    @Insert("INSERT INTO repository_branch (repository_id, branch_name, commit_hash, default_flag, status, created_time, updated_time) VALUES (#{repositoryId}, #{branchName}, #{commitHash}, #{defaultFlag}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(RepositoryBranch entity);

    @Update("UPDATE repository_branch SET repository_id = #{repositoryId}, branch_name = #{branchName}, commit_hash = #{commitHash}, default_flag = #{defaultFlag}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(RepositoryBranch entity);

    @Delete("DELETE FROM repository_branch WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM repository_branch")
    long countAll();


    @Update("UPDATE repository_branch SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);


}
