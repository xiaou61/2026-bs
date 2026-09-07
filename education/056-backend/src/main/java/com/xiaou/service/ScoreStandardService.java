package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.ScoreStandard;
import com.xiaou.mapper.ScoreStandardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScoreStandardService {

    @Autowired
    private ScoreStandardMapper scoreStandardMapper;

    public List<ScoreStandard> getByCompetitionId(Long competitionId) {
        return scoreStandardMapper.selectList(new LambdaQueryWrapper<ScoreStandard>()
                .eq(ScoreStandard::getCompetitionId, competitionId)
                .orderByAsc(ScoreStandard::getSort));
    }

    @Transactional
    public void save(Long competitionId, List<ScoreStandard> standards) {
        scoreStandardMapper.delete(new LambdaQueryWrapper<ScoreStandard>()
                .eq(ScoreStandard::getCompetitionId, competitionId));
        for (int i = 0; i < standards.size(); i++) {
            ScoreStandard standard = standards.get(i);
            standard.setId(null);
            standard.setCompetitionId(competitionId);
            standard.setSort(i + 1);
            scoreStandardMapper.insert(standard);
        }
    }
}
