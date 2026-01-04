package com.programming.learning.mapper;

import com.programming.learning.entity.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 回答Mapper接口
 */
@Mapper
public interface AnswerMapper {

    int insert(Answer answer);

    int deleteById(Long id);

    int update(Answer answer);

    Answer selectById(Long id);

    List<Answer> selectByQuestionId(@Param("questionId") Long questionId);

    List<Answer> selectByUserId(@Param("userId") Long userId);

    Long countByQuestionId(Long questionId);

    int incrementLikeCount(Long id);

    int decrementLikeCount(Long id);

    int incrementCommentCount(Long id);

    int decrementCommentCount(Long id);

    int updateAcceptStatus(@Param("id") Long id, @Param("isAccepted") Boolean isAccepted);
}
