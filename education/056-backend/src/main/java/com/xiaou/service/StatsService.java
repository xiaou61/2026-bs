package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CompetitionMapper competitionMapper;

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private JudgeAssignmentMapper judgeAssignmentMapper;

    @Autowired
    private ScoreRecordMapper scoreRecordMapper;

    public Map<String, Object> getDashboard() {
        Map<String, Object> result = new HashMap<>();
        result.put("userCount", userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getRole, 2)));
        result.put("competitionCount", competitionMapper.selectCount(null));
        result.put("workCount", workMapper.selectCount(null));
        result.put("judgeCount", userMapper.selectCount(new LambdaQueryWrapper<User>().eq(User::getRole, 1)));
        result.put("activeCompetitionCount", competitionMapper.selectCount(new LambdaQueryWrapper<Competition>().eq(Competition::getStatus, 1)));
        List<Competition> recentCompetitions = competitionMapper.selectList(new LambdaQueryWrapper<Competition>()
                .orderByDesc(Competition::getCreateTime)
                .last("LIMIT 5"));
        result.put("recentCompetitions", recentCompetitions);
        return result;
    }

    public Map<String, Object> getCompetitionStats(Long competitionId) {
        Map<String, Object> result = new HashMap<>();
        Long workCount = workMapper.selectCount(new LambdaQueryWrapper<Work>()
                .eq(Work::getCompetitionId, competitionId));
        result.put("workCount", workCount);
        Long passedCount = workMapper.selectCount(new LambdaQueryWrapper<Work>()
                .eq(Work::getCompetitionId, competitionId)
                .eq(Work::getStatus, 1));
        result.put("passedCount", passedCount);
        Long scoredCount = workMapper.selectCount(new LambdaQueryWrapper<Work>()
                .eq(Work::getCompetitionId, competitionId)
                .eq(Work::getStatus, 1)
                .isNotNull(Work::getFinalScore));
        result.put("scoredCount", scoredCount);
        List<Work> works = workMapper.selectList(new LambdaQueryWrapper<Work>()
                .eq(Work::getCompetitionId, competitionId)
                .eq(Work::getStatus, 1)
                .isNotNull(Work::getFinalScore)
                .select(Work::getFinalScore));
        if (!works.isEmpty()) {
            BigDecimal total = BigDecimal.ZERO;
            for (Work work : works) {
                total = total.add(work.getFinalScore());
            }
            BigDecimal avg = total.divide(BigDecimal.valueOf(works.size()), 2, RoundingMode.HALF_UP);
            result.put("avgScore", avg);
        } else {
            result.put("avgScore", 0);
        }
        return result;
    }

    public List<Map<String, Object>> getScoreProgress(Long competitionId) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<JudgeAssignment> assignments = judgeAssignmentMapper.selectList(new LambdaQueryWrapper<JudgeAssignment>()
                .eq(JudgeAssignment::getCompetitionId, competitionId));
        Long totalWorks = workMapper.selectCount(new LambdaQueryWrapper<Work>()
                .eq(Work::getCompetitionId, competitionId)
                .eq(Work::getStatus, 1));
        for (JudgeAssignment assignment : assignments) {
            Map<String, Object> item = new HashMap<>();
            User judge = userMapper.selectById(assignment.getJudgeId());
            item.put("judgeId", assignment.getJudgeId());
            item.put("judgeName", judge != null ? judge.getNickname() : "");
            item.put("total", totalWorks);
            Long completedCount = scoreRecordMapper.selectCount(new LambdaQueryWrapper<ScoreRecord>()
                    .eq(ScoreRecord::getJudgeId, assignment.getJudgeId())
                    .eq(ScoreRecord::getStatus, 1)
                    .inSql(ScoreRecord::getWorkId, "SELECT id FROM work WHERE competition_id = " + competitionId + " AND status = 1"));
            item.put("completed", completedCount);
            result.add(item);
        }
        return result;
    }
}
