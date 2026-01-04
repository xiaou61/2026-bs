package com.programming.learning.mapper;

import com.programming.learning.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 问题Mapper接口
 */
@Mapper
public interface QuestionMapper {

    int insert(Question question);

    int deleteById(Long id);

    int update(Question question);

    Question selectById(Long id);

    List<Question> selectAll(@Param("offset") Integer offset, @Param("limit") Integer limit);

    Long countAll();

    List<Question> selectByUserId(@Param("userId") Long userId);

    List<Question> selectByTag(@Param("tag") String tag, @Param("offset") Integer offset, @Param("limit") Integer limit);

    List<Question> selectHot(@Param("limit") Integer limit);

    int incrementViewCount(Long id);

    int incrementAnswerCount(Long id);

    int decrementAnswerCount(Long id);

    int incrementLikeCount(Long id);

    int decrementLikeCount(Long id);

    int updateAcceptedAnswer(@Param("id") Long id, @Param("acceptedAnswerId") Long acceptedAnswerId);

    List<Question> search(@Param("keyword") String keyword, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
