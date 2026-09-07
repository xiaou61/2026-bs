package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.*;
import com.xiaou.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    @Autowired
    private ScoreRecordMapper scoreRecordMapper;

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ScoreStandardMapper scoreStandardMapper;

    @Autowired
    private JudgeAssignmentMapper judgeAssignmentMapper;

    @Autowired
    private CompetitionMapper competitionMapper;

    public Page<Work> getPendingWorks(Long judgeId, Integer pageNum, Integer pageSize, Long competitionId) {
        List<Long> competitionIds;
        if (competitionId != null) {
            competitionIds = List.of(competitionId);
        } else {
            List<JudgeAssignment> assignments = judgeAssignmentMapper.selectList(new LambdaQueryWrapper<JudgeAssignment>()
                    .eq(JudgeAssignment::getJudgeId, judgeId));
            competitionIds = assignments.stream().map(JudgeAssignment::getCompetitionId).toList();
        }
        if (competitionIds.isEmpty()) {
            return new Page<>(pageNum, pageSize);
        }
        Page<Work> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Work> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Work::getCompetitionId, competitionIds);
        wrapper.eq(Work::getStatus, 1);
        wrapper.orderByAsc(Work::getSubmitTime);
        Page<Work> result = workMapper.selectPage(page, wrapper);
        result.getRecords().forEach(work -> {
            Competition competition = competitionMapper.selectById(work.getCompetitionId());
            if (competition != null) {
                work.setCompetitionTitle(competition.getTitle());
            }
            User user = userMapper.selectById(work.getUserId());
            if (user != null) {
                work.setAuthorName(user.getNickname());
            }
            ScoreRecord record = scoreRecordMapper.selectOne(new LambdaQueryWrapper<ScoreRecord>()
                    .eq(ScoreRecord::getWorkId, work.getId())
                    .eq(ScoreRecord::getJudgeId, judgeId));
            work.setScoredCount(record != null && record.getStatus() == 1 ? 1 : 0);
        });
        return result;
    }

    @Transactional
    public void submitScore(Long judgeId, Long workId, List<Score> scores, String comment) {
        Work work = workMapper.selectById(workId);
        if (work == null) {
            throw new BusinessException("作品不存在");
        }
        JudgeAssignment assignment = judgeAssignmentMapper.selectOne(new LambdaQueryWrapper<JudgeAssignment>()
                .eq(JudgeAssignment::getCompetitionId, work.getCompetitionId())
                .eq(JudgeAssignment::getJudgeId, judgeId));
        if (assignment == null) {
            throw new BusinessException("您不是该竞赛的评委");
        }
        scoreMapper.delete(new LambdaQueryWrapper<Score>()
                .eq(Score::getWorkId, workId)
                .eq(Score::getJudgeId, judgeId));
        BigDecimal totalScore = BigDecimal.ZERO;
        for (Score score : scores) {
            score.setId(null);
            score.setWorkId(workId);
            score.setJudgeId(judgeId);
            scoreMapper.insert(score);
            ScoreStandard standard = scoreStandardMapper.selectById(score.getStandardId());
            if (standard != null) {
                totalScore = totalScore.add(score.getScore().multiply(standard.getWeight()));
            }
        }
        ScoreRecord record = scoreRecordMapper.selectOne(new LambdaQueryWrapper<ScoreRecord>()
                .eq(ScoreRecord::getWorkId, workId)
                .eq(ScoreRecord::getJudgeId, judgeId));
        if (record == null) {
            record = new ScoreRecord();
            record.setWorkId(workId);
            record.setJudgeId(judgeId);
            record.setTotalScore(totalScore);
            record.setComment(comment);
            record.setStatus(1);
            scoreRecordMapper.insert(record);
        } else {
            record.setTotalScore(totalScore);
            record.setComment(comment);
            record.setStatus(1);
            scoreRecordMapper.updateById(record);
        }
        calculateFinalScore(workId);
    }

    public Map<String, Object> getScoreDetail(Long workId) {
        Map<String, Object> result = new HashMap<>();
        List<ScoreRecord> records = scoreRecordMapper.selectList(new LambdaQueryWrapper<ScoreRecord>()
                .eq(ScoreRecord::getWorkId, workId)
                .eq(ScoreRecord::getStatus, 1));
        records.forEach(r -> {
            User judge = userMapper.selectById(r.getJudgeId());
            if (judge != null) {
                r.setJudgeName(judge.getNickname());
            }
        });
        result.put("records", records);
        List<Score> scores = scoreMapper.selectList(new LambdaQueryWrapper<Score>()
                .eq(Score::getWorkId, workId));
        scores.forEach(s -> {
            ScoreStandard standard = scoreStandardMapper.selectById(s.getStandardId());
            if (standard != null) {
                s.setStandardName(standard.getName());
            }
        });
        result.put("scores", scores);
        Work work = workMapper.selectById(workId);
        result.put("finalScore", work != null ? work.getFinalScore() : null);
        return result;
    }

    public Map<String, Object> getMyScore(Long judgeId, Long workId) {
        Map<String, Object> result = new HashMap<>();
        ScoreRecord record = scoreRecordMapper.selectOne(new LambdaQueryWrapper<ScoreRecord>()
                .eq(ScoreRecord::getWorkId, workId)
                .eq(ScoreRecord::getJudgeId, judgeId));
        result.put("record", record);
        List<Score> scores = scoreMapper.selectList(new LambdaQueryWrapper<Score>()
                .eq(Score::getWorkId, workId)
                .eq(Score::getJudgeId, judgeId));
        result.put("scores", scores);
        return result;
    }

    private void calculateFinalScore(Long workId) {
        List<ScoreRecord> records = scoreRecordMapper.selectList(new LambdaQueryWrapper<ScoreRecord>()
                .eq(ScoreRecord::getWorkId, workId)
                .eq(ScoreRecord::getStatus, 1));
        if (records.isEmpty()) {
            return;
        }
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal max = records.get(0).getTotalScore();
        BigDecimal min = records.get(0).getTotalScore();
        for (ScoreRecord record : records) {
            total = total.add(record.getTotalScore());
            if (record.getTotalScore().compareTo(max) > 0) {
                max = record.getTotalScore();
            }
            if (record.getTotalScore().compareTo(min) < 0) {
                min = record.getTotalScore();
            }
        }
        BigDecimal finalScore;
        if (records.size() > 2) {
            finalScore = total.subtract(max).subtract(min).divide(BigDecimal.valueOf(records.size() - 2), 2, RoundingMode.HALF_UP);
        } else {
            finalScore = total.divide(BigDecimal.valueOf(records.size()), 2, RoundingMode.HALF_UP);
        }
        Work work = new Work();
        work.setId(workId);
        work.setFinalScore(finalScore);
        workMapper.updateById(work);
    }
}
