package com.xiaou.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaou.entity.Award;
import com.xiaou.entity.User;
import com.xiaou.entity.Work;
import com.xiaou.mapper.AwardMapper;
import com.xiaou.mapper.UserMapper;
import com.xiaou.mapper.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AwardService {

    @Autowired
    private AwardMapper awardMapper;

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private UserMapper userMapper;

    public List<Award> getByCompetitionId(Long competitionId) {
        List<Award> list = awardMapper.selectList(new LambdaQueryWrapper<Award>()
                .eq(Award::getCompetitionId, competitionId)
                .orderByAsc(Award::getCreateTime));
        list.forEach(this::fillAwardInfo);
        return list;
    }

    public List<Award> getMyAwards(Long userId) {
        List<Award> list = awardMapper.selectList(new LambdaQueryWrapper<Award>()
                .eq(Award::getUserId, userId)
                .orderByDesc(Award::getCreateTime));
        list.forEach(this::fillAwardInfo);
        return list;
    }

    @Transactional
    public void generate(Long competitionId, List<Map<String, Object>> awards) {
        awardMapper.delete(new LambdaQueryWrapper<Award>()
                .eq(Award::getCompetitionId, competitionId));
        List<Work> works = workMapper.selectList(new LambdaQueryWrapper<Work>()
                .eq(Work::getCompetitionId, competitionId)
                .eq(Work::getStatus, 1)
                .isNotNull(Work::getFinalScore)
                .orderByDesc(Work::getFinalScore));
        for (int i = 0; i < works.size(); i++) {
            Work work = works.get(i);
            work.setRank(i + 1);
            workMapper.updateById(work);
        }
        for (Map<String, Object> awardConfig : awards) {
            Integer rank = (Integer) awardConfig.get("rank");
            String level = (String) awardConfig.get("level");
            if (rank != null && rank <= works.size()) {
                Work work = works.get(rank - 1);
                Award award = new Award();
                award.setCompetitionId(competitionId);
                award.setWorkId(work.getId());
                award.setUserId(work.getUserId());
                award.setAwardLevel(level);
                awardMapper.insert(award);
            }
        }
    }

    private void fillAwardInfo(Award award) {
        if (award.getWorkId() != null) {
            Work work = workMapper.selectById(award.getWorkId());
            if (work != null) {
                award.setWorkTitle(work.getTitle());
                award.setScore(work.getFinalScore());
                award.setRank(work.getRank());
            }
        }
        if (award.getUserId() != null) {
            User user = userMapper.selectById(award.getUserId());
            if (user != null) {
                award.setUserName(user.getNickname());
            }
        }
    }
}
