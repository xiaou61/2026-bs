package com.programming.learning.mapper;

import com.programming.learning.entity.CodeSnippet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代码片段Mapper接口
 */
@Mapper
public interface CodeSnippetMapper {

    int insert(CodeSnippet codeSnippet);

    int deleteById(Long id);

    int update(CodeSnippet codeSnippet);

    CodeSnippet selectById(Long id);

    List<CodeSnippet> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);

    Long countAll();

    List<CodeSnippet> selectByUserId(@Param("userId") Long userId);

    List<CodeSnippet> selectByLanguage(@Param("language") String language, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<CodeSnippet> selectHot(@Param("limit") Integer limit);

    int incrementViewCount(Long id);

    int incrementLikeCount(Long id);

    int decrementLikeCount(Long id);

    int incrementForkCount(Long id);

    List<CodeSnippet> search(@Param("keyword") String keyword, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
