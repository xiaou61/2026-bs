package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.JudgeAssignment;
import com.xiaou.entity.User;
import com.xiaou.mapper.JudgeAssignmentMapper;
import com.xiaou.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JudgeService {

    @Autowired
    private JudgeAssignmentMapper judgeAssignmentMapper;

    @Autowired
    private UserMapper userMapper;

    public List<JudgeAssignment> getByCompetitionId(Long competitionId) {
        List<JudgeAssignment> list = judgeAssignmentMapper.selectList(new LambdaQueryWrapper<JudgeAssignment>()
                .eq(JudgeAssignment::getCompetitionId, competitionId));
        list.forEach(ja -> {
            User judge = userMapper.selectById(ja.getJudgeId());
            if (judge != null) {
                ja.setJudgeName(judge.getNickname());
            }
        });
        return list;
    }

    @Transactional
    public void assign(Long competitionId, List<Long> judgeIds) {
        judgeAssignmentMapper.delete(new LambdaQueryWrapper<JudgeAssignment>()
                .eq(JudgeAssignment::getCompetitionId, competitionId));
        for (Long judgeId : judgeIds) {
            JudgeAssignment assignment = new JudgeAssignment();
            assignment.setCompetitionId(competitionId);
            assignment.setJudgeId(judgeId);
            judgeAssignmentMapper.insert(assignment);
        }
    }

    public List<Long> getCompetitionIdsByJudge(Long judgeId) {
        List<JudgeAssignment> list = judgeAssignmentMapper.selectList(new LambdaQueryWrapper<JudgeAssignment>()
                .eq(JudgeAssignment::getJudgeId, judgeId)
                .select(JudgeAssignment::getCompetitionId));
        return list.stream().map(JudgeAssignment::getCompetitionId).toList();
    }
}
