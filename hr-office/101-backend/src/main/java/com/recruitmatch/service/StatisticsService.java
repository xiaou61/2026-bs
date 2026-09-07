package com.recruitmatch.service;

import com.recruitmatch.entity.InterviewPlan;
import com.recruitmatch.entity.JobPosition;
import com.recruitmatch.entity.MatchResult;
import com.recruitmatch.entity.ParsingResult;
import com.recruitmatch.mapper.CandidateProfileMapper;
import com.recruitmatch.mapper.InterviewPlanMapper;
import com.recruitmatch.mapper.JobPositionMapper;
import com.recruitmatch.mapper.MatchResultMapper;
import com.recruitmatch.mapper.ParsingResultMapper;
import com.recruitmatch.mapper.ResumeFileMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {
    @Autowired
    private CandidateProfileMapper candidateProfileMapper;

    @Autowired
    private ResumeFileMapper resumeFileMapper;

    @Autowired
    private JobPositionMapper jobPositionMapper;

    @Autowired
    private ParsingResultMapper parsingResultMapper;

    @Autowired
    private MatchResultMapper matchResultMapper;

    @Autowired
    private InterviewPlanMapper interviewPlanMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> result = new HashMap<>();
        result.put("candidateCount", candidateProfileMapper.selectCount(null));
        result.put("resumeCount", resumeFileMapper.selectCount(null));
        result.put("jobCount", jobPositionMapper.selectCount(null));
        result.put("openJobs", jobPositionMapper.selectCount(new LambdaQueryWrapper<JobPosition>().eq(JobPosition::getStatus, 1)));
        result.put("parseResultCount", parsingResultMapper.selectCount(null));
        result.put("matchResultCount", matchResultMapper.selectCount(null));
        result.put("strongRecommend", matchResultMapper.selectCount(new LambdaQueryWrapper<MatchResult>().eq(MatchResult::getRecommendLevel, "强推荐")));
        result.put("pendingInterview", interviewPlanMapper.selectCount(new LambdaQueryWrapper<InterviewPlan>().eq(InterviewPlan::getStatus, 0)));
        result.put("averageParseScore", averageParseScore());
        result.put("averageMatchScore", averageMatchScore());
        return result;
    }

    private BigDecimal averageParseScore() {
        List<ParsingResult> list = parsingResultMapper.selectList(null);
        BigDecimal total = BigDecimal.ZERO;
        for (ParsingResult item : list) {
            total = total.add(item.getScore() == null ? BigDecimal.ZERO : item.getScore());
        }
        return list.isEmpty() ? BigDecimal.ZERO : total.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal averageMatchScore() {
        List<MatchResult> list = matchResultMapper.selectList(null);
        BigDecimal total = BigDecimal.ZERO;
        for (MatchResult item : list) {
            total = total.add(item.getMatchScore() == null ? BigDecimal.ZERO : item.getMatchScore());
        }
        return list.isEmpty() ? BigDecimal.ZERO : total.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
    }
}
