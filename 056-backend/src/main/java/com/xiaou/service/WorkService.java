package com.xiaou.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaou.common.BusinessException;
import com.xiaou.entity.Competition;
import com.xiaou.entity.ScoreRecord;
import com.xiaou.entity.User;
import com.xiaou.entity.Work;
import com.xiaou.mapper.CompetitionMapper;
import com.xiaou.mapper.ScoreRecordMapper;
import com.xiaou.mapper.UserMapper;
import com.xiaou.mapper.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WorkService {

    @Autowired
    private WorkMapper workMapper;

    @Autowired
    private CompetitionMapper competitionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ScoreRecordMapper scoreRecordMapper;

    public Page<Work> getList(Integer pageNum, Integer pageSize, Long competitionId, Integer status, String keyword) {
        Page<Work> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Work> wrapper = new LambdaQueryWrapper<>();
        if (competitionId != null) {
            wrapper.eq(Work::getCompetitionId, competitionId);
        }
        if (status != null) {
            wrapper.eq(Work::getStatus, status);
        }
        if (StrUtil.isNotBlank(keyword)) {
            wrapper.like(Work::getTitle, keyword);
        }
        wrapper.orderByDesc(Work::getSubmitTime);
        Page<Work> result = workMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillWorkInfo);
        return result;
    }

    public Page<Work> getMyWorks(Long userId, Integer pageNum, Integer pageSize) {
        Page<Work> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Work> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Work::getUserId, userId);
        wrapper.orderByDesc(Work::getSubmitTime);
        Page<Work> result = workMapper.selectPage(page, wrapper);
        result.getRecords().forEach(this::fillWorkInfo);
        return result;
    }

    public Work getById(Long id) {
        Work work = workMapper.selectById(id);
        if (work != null) {
            fillWorkInfo(work);
        }
        return work;
    }

    public void submit(Work work, Long userId) {
        Competition competition = competitionMapper.selectById(work.getCompetitionId());
        if (competition == null) {
            throw new BusinessException("竞赛不存在");
        }
        if (competition.getStatus() != 1) {
            throw new BusinessException("竞赛未开始或已结束");
        }
        if (competition.getSubmitDeadline() != null && LocalDateTime.now().isAfter(competition.getSubmitDeadline())) {
            throw new BusinessException("已超过提交截止时间");
        }
        Work exist = workMapper.selectOne(new LambdaQueryWrapper<Work>()
                .eq(Work::getCompetitionId, work.getCompetitionId())
                .eq(Work::getUserId, userId)
                .ne(Work::getStatus, 3));
        if (exist != null) {
            throw new BusinessException("您已提交过作品");
        }
        int wordCount = work.getContent() != null ? work.getContent().length() : 0;
        if (competition.getMinWords() != null && wordCount < competition.getMinWords()) {
            throw new BusinessException("字数不能少于" + competition.getMinWords() + "字");
        }
        if (competition.getMaxWords() != null && wordCount > competition.getMaxWords()) {
            throw new BusinessException("字数不能超过" + competition.getMaxWords() + "字");
        }
        work.setUserId(userId);
        work.setWordCount(wordCount);
        work.setStatus(0);
        work.setSubmitTime(LocalDateTime.now());
        workMapper.insert(work);
    }

    public void update(Work work, Long userId) {
        Work old = workMapper.selectById(work.getId());
        if (old == null) {
            throw new BusinessException("作品不存在");
        }
        if (!old.getUserId().equals(userId)) {
            throw new BusinessException("无权修改");
        }
        if (old.getStatus() != 0 && old.getStatus() != 2) {
            throw new BusinessException("当前状态不允许修改");
        }
        int wordCount = work.getContent() != null ? work.getContent().length() : 0;
        work.setWordCount(wordCount);
        work.setStatus(0);
        workMapper.updateById(work);
    }

    public void withdraw(Long id, Long userId) {
        Work work = workMapper.selectById(id);
        if (work == null) {
            throw new BusinessException("作品不存在");
        }
        if (!work.getUserId().equals(userId)) {
            throw new BusinessException("无权操作");
        }
        if (work.getStatus() != 0) {
            throw new BusinessException("当前状态不允许撤回");
        }
        work.setStatus(3);
        workMapper.updateById(work);
    }

    public void audit(Long id, Integer status, String rejectReason) {
        Work work = new Work();
        work.setId(id);
        work.setStatus(status);
        if (status == 2) {
            work.setRejectReason(rejectReason);
        }
        workMapper.updateById(work);
    }

    private void fillWorkInfo(Work work) {
        if (work.getCompetitionId() != null) {
            Competition competition = competitionMapper.selectById(work.getCompetitionId());
            if (competition != null) {
                work.setCompetitionTitle(competition.getTitle());
            }
        }
        if (work.getUserId() != null) {
            User user = userMapper.selectById(work.getUserId());
            if (user != null) {
                work.setAuthorName(user.getNickname());
            }
        }
        Long scoredCount = scoreRecordMapper.selectCount(new LambdaQueryWrapper<ScoreRecord>()
                .eq(ScoreRecord::getWorkId, work.getId())
                .eq(ScoreRecord::getStatus, 1));
        work.setScoredCount(scoredCount.intValue());
    }
}
