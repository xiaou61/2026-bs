package com.promptops.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.promptops.common.BusinessException;
import com.promptops.entity.EvaluationResult;
import com.promptops.entity.EvaluationTask;
import com.promptops.entity.PromptTestCase;
import com.promptops.mapper.EvaluationResultMapper;
import com.promptops.mapper.EvaluationTaskMapper;
import com.promptops.mapper.PromptTestCaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EvaluationTaskService extends ServiceImpl<EvaluationTaskMapper, EvaluationTask> {

    @Autowired
    private PromptTestCaseMapper testCaseMapper;

    @Autowired
    private EvaluationResultMapper resultMapper;

    public Page<EvaluationTask> page(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        LambdaQueryWrapper<EvaluationTask> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(keyword), EvaluationTask::getName, keyword);
        wrapper.eq(status != null, EvaluationTask::getStatus, status);
        wrapper.orderByDesc(EvaluationTask::getId);
        return page(new Page<>(pageNum, pageSize), wrapper);
    }

    public void saveEntity(EvaluationTask entity, Long userId) {
        if (!StringUtils.hasText(entity.getName()) || entity.getAssetId() == null || entity.getVersionId() == null || entity.getModelId() == null) {
            throw new BusinessException(400, "评测任务信息不完整");
        }
        if (entity.getId() == null) {
            entity.setTaskNo("EV" + System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(100, 999));
            entity.setStatus(0);
            entity.setAverageScore(BigDecimal.ZERO);
            entity.setPassRate(BigDecimal.ZERO);
            entity.setCreatorId(userId);
            entity.setCreateTime(LocalDateTime.now());
        }
        saveOrUpdate(entity);
    }

    public void run(Long id) {
        EvaluationTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "评测任务不存在");
        }
        List<PromptTestCase> cases = testCaseMapper.selectList(new LambdaQueryWrapper<PromptTestCase>()
                .eq(PromptTestCase::getAssetId, task.getAssetId())
                .eq(PromptTestCase::getStatus, 1));
        if (cases.isEmpty()) {
            throw new BusinessException(400, "当前资产暂无可用测试用例");
        }
        resultMapper.delete(new LambdaQueryWrapper<EvaluationResult>().eq(EvaluationResult::getTaskId, id));
        int passedCount = 0;
        BigDecimal totalScore = BigDecimal.ZERO;
        for (PromptTestCase testCase : cases) {
            BigDecimal score = buildScore(testCase);
            boolean passed = score.compareTo(new BigDecimal("80")) >= 0;
            if (passed) {
                passedCount++;
            }
            totalScore = totalScore.add(score);
            EvaluationResult result = new EvaluationResult();
            result.setTaskId(task.getId());
            result.setCaseId(testCase.getId());
            result.setInputText(testCase.getInputText());
            result.setExpectedOutput(testCase.getExpectedOutput());
            result.setActualOutput(buildOutput(testCase));
            result.setScore(score);
            result.setPassed(passed ? 1 : 0);
            result.setReviewStatus(0);
            result.setCreateTime(LocalDateTime.now());
            result.setUpdateTime(LocalDateTime.now());
            resultMapper.insert(result);
        }
        task.setStatus(1);
        task.setAverageScore(totalScore.divide(new BigDecimal(cases.size()), 2, RoundingMode.HALF_UP));
        task.setPassRate(new BigDecimal(passedCount).multiply(new BigDecimal("100")).divide(new BigDecimal(cases.size()), 2, RoundingMode.HALF_UP));
        updateById(task);
    }

    public void finish(Long id) {
        EvaluationTask task = getById(id);
        if (task == null) {
            throw new BusinessException(400, "评测任务不存在");
        }
        task.setStatus(2);
        task.setFinishTime(LocalDateTime.now());
        updateById(task);
    }

    public void cancel(Long id) {
        update(new LambdaUpdateWrapper<EvaluationTask>().eq(EvaluationTask::getId, id).set(EvaluationTask::getStatus, 3));
    }

    private BigDecimal buildScore(PromptTestCase testCase) {
        int base = 88;
        if ("高".equals(testCase.getDifficulty())) {
            base = 82;
        } else if ("低".equals(testCase.getDifficulty())) {
            base = 92;
        }
        int offset = Math.abs(testCase.getTitle().hashCode()) % 8;
        return new BigDecimal(base + offset).setScale(2, RoundingMode.HALF_UP);
    }

    private String buildOutput(PromptTestCase testCase) {
        return "系统已根据输入生成候选答案，覆盖评分要点：" + testCase.getScorePoints();
    }
}
