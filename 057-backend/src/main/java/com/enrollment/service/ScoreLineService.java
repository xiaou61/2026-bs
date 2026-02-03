package com.enrollment.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enrollment.entity.ScoreLine;
import com.enrollment.mapper.ScoreLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreLineService {

    @Autowired
    private ScoreLineMapper scoreLineMapper;

    public IPage<ScoreLine> getPage(Integer pageNum, Integer pageSize, Integer year, String province) {
        Page<ScoreLine> page = new Page<>(pageNum, pageSize);
        return scoreLineMapper.selectPageWithMajor(page, year, province);
    }

    public void add(ScoreLine scoreLine) {
        scoreLineMapper.insert(scoreLine);
    }

    public void update(ScoreLine scoreLine) {
        scoreLineMapper.updateById(scoreLine);
    }

    public void delete(Long id) {
        scoreLineMapper.deleteById(id);
    }
}
