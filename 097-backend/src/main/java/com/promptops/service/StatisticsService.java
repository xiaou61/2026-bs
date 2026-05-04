package com.promptops.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.promptops.entity.EvaluationResult;
import com.promptops.entity.EvaluationTask;
import com.promptops.entity.PromptAsset;
import com.promptops.entity.PromptFeedback;
import com.promptops.entity.PromptVersion;
import com.promptops.mapper.EvaluationResultMapper;
import com.promptops.mapper.EvaluationTaskMapper;
import com.promptops.mapper.PromptAssetMapper;
import com.promptops.mapper.PromptFeedbackMapper;
import com.promptops.mapper.PromptVersionMapper;
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
    private PromptAssetMapper assetMapper;

    @Autowired
    private PromptVersionMapper versionMapper;

    @Autowired
    private EvaluationTaskMapper taskMapper;

    @Autowired
    private EvaluationResultMapper resultMapper;

    @Autowired
    private PromptFeedbackMapper feedbackMapper;

    public Map<String, Object> dashboard() {
        Map<String, Object> result = new HashMap<>();
        Long assetCount = assetMapper.selectCount(null);
        Long versionCount = versionMapper.selectCount(null);
        Long taskCount = taskMapper.selectCount(null);
        Long feedbackPending = feedbackMapper.selectCount(new LambdaQueryWrapper<PromptFeedback>().eq(PromptFeedback::getStatus, 0));
        List<EvaluationResult> results = resultMapper.selectList(null);
        BigDecimal total = BigDecimal.ZERO;
        int passed = 0;
        for (EvaluationResult item : results) {
            total = total.add(item.getScore() == null ? BigDecimal.ZERO : item.getScore());
            if (item.getPassed() != null && item.getPassed() == 1) {
                passed++;
            }
        }
        BigDecimal avg = results.isEmpty() ? BigDecimal.ZERO : total.divide(new BigDecimal(results.size()), 2, RoundingMode.HALF_UP);
        BigDecimal passRate = results.isEmpty() ? BigDecimal.ZERO : new BigDecimal(passed).multiply(new BigDecimal("100")).divide(new BigDecimal(results.size()), 2, RoundingMode.HALF_UP);
        result.put("assetCount", assetCount);
        result.put("versionCount", versionCount);
        result.put("taskCount", taskCount);
        result.put("feedbackPending", feedbackPending);
        result.put("averageScore", avg);
        result.put("passRate", passRate);
        result.put("publishedAssets", assetMapper.selectCount(new LambdaQueryWrapper<PromptAsset>().eq(PromptAsset::getStatus, 1)));
        result.put("publishedVersions", versionMapper.selectCount(new LambdaQueryWrapper<PromptVersion>().eq(PromptVersion::getStatus, 1)));
        result.put("finishedTasks", taskMapper.selectCount(new LambdaQueryWrapper<EvaluationTask>().eq(EvaluationTask::getStatus, 2)));
        return result;
    }
}
