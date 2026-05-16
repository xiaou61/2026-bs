package com.foodinspect.mapper;

import com.foodinspect.entity.TestResult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TestResultMapper {
    @Select({
        "<script>",
        "SELECT * FROM test_result",
        "<where>",
        "<if test='keyword != null and keyword != \"\"'> AND (result_no LIKE CONCAT('%',#{keyword},'%') OR food_name LIKE CONCAT('%',#{keyword},'%') OR test_conclusion LIKE CONCAT('%',#{keyword},'%') OR tester_name LIKE CONCAT('%',#{keyword},'%'))</if>",
        "<if test='status != null and status != \"\"'> AND status = #{status}</if>",
        "</where>",
        "ORDER BY id DESC",
        "</script>"
    })
    List<TestResult> selectPage(@Param("keyword") String keyword, @Param("status") String status);

    @Select("SELECT * FROM test_result WHERE id = #{id}")
    TestResult selectById(Long id);

    @Insert("INSERT INTO test_result (result_no, food_name, test_conclusion, test_time, tester_name, status, created_time, updated_time) VALUES (#{resultNo}, #{foodName}, #{testConclusion}, #{testTime}, #{testerName}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TestResult entity);

    @Update("UPDATE test_result SET result_no = #{resultNo}, food_name = #{foodName}, test_conclusion = #{testConclusion}, test_time = #{testTime}, tester_name = #{testerName}, status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int update(TestResult entity);

    @Delete("DELETE FROM test_result WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT COUNT(*) FROM test_result")
    long countAll();

    @Update("UPDATE test_result SET status = #{status}, updated_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}






