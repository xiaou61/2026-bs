package com.enrollment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.Score;
import com.enrollment.mapper.ScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {

    @Autowired
    private ScoreMapper scoreMapper;

    public IPage<Score> getPage(Integer pageNum, Integer pageSize, String studentName, Integer year) {
        Page<Score> page = new Page<>(pageNum, pageSize);
        return scoreMapper.selectPageWithStudent(page, studentName, year);
    }

    public void add(Score score) {
        score.setTotalScore(score.getChinese() + score.getMath() + score.getEnglish() + score.getComprehensive());
        scoreMapper.insert(score);
    }

    public void update(Score score) {
        score.setTotalScore(score.getChinese() + score.getMath() + score.getEnglish() + score.getComprehensive());
        scoreMapper.updateById(score);
    }

    public void delete(Long id) {
        scoreMapper.deleteById(id);
    }

    public void batchImport(List<Score> scores) {
        for (Score score : scores) {
            score.setTotalScore(score.getChinese() + score.getMath() + score.getEnglish() + score.getComprehensive());
            scoreMapper.insert(score);
        }
    }
}
