package com.teachres.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.teachres.common.BusinessException;
import com.teachres.entity.EvalIndicator;
import com.teachres.entity.EvalRecord;
import com.teachres.entity.EvalRecordItem;
import com.teachres.entity.EvalTask;
import com.teachres.mapper.EvalIndicatorMapper;
import com.teachres.mapper.EvalRecordItemMapper;
import com.teachres.mapper.EvalRecordMapper;
import com.teachres.mapper.EvalTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EvaluationService {

    @Autowired
    private EvalTaskMapper taskMapper;

    @Autowired
    private EvalIndicatorMapper indicatorMapper;

    @Autowired
    private EvalRecordMapper recordMapper;

    @Autowired
    private EvalRecordItemMapper recordItemMapper;

    public PageInfo<Map<String, Object>> myList(Long studentId, Long taskId, Long courseId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = recordMapper.selectMyList(studentId, taskId, courseId);
        return new PageInfo<>(list);
    }

    public Map<String, Object> detail(Long recordId) {
        EvalRecord record = recordMapper.selectById(recordId);
        if (record == null) {
            throw new BusinessException("评价记录不存在");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("record", record);
        result.put("items", recordItemMapper.selectByRecordId(recordId));
        return result;
    }

    @SuppressWarnings("unchecked")
    public void submit(Map<String, Object> params, Long userId, String role) {
        if (!"student".equals(role)) {
            throw new BusinessException("仅学生可提交课程评价");
        }
        if (params.get("taskId") == null) {
            throw new BusinessException("任务ID不能为空");
        }
        Long taskId = Long.valueOf(String.valueOf(params.get("taskId")));
        EvalTask task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new BusinessException("评价任务不存在");
        }
        if (task.getStatus() == null || task.getStatus() != 1) {
            throw new BusinessException("评价任务未启用");
        }

        LocalDateTime now = LocalDateTime.now();
        if (task.getStartTime() != null && now.isBefore(task.getStartTime())) {
            throw new BusinessException("评价尚未开始");
        }
        if (task.getEndTime() != null && now.isAfter(task.getEndTime())) {
            throw new BusinessException("评价已结束");
        }

        EvalRecord exists = recordMapper.selectByTaskAndStudent(taskId, userId);
        if (exists != null) {
            throw new BusinessException("该任务已提交过评价");
        }

        Object rawItems = params.get("items");
        if (!(rawItems instanceof List)) {
            throw new BusinessException("评分明细不能为空");
        }
        List<Map<String, Object>> items = (List<Map<String, Object>>) rawItems;
        if (items.isEmpty()) {
            throw new BusinessException("评分明细不能为空");
        }

        Map<Long, EvalIndicator> indicatorMap = indicatorMapper.selectEnabledList().stream()
                .collect(Collectors.toMap(EvalIndicator::getId, i -> i));

        BigDecimal weightedSum = BigDecimal.ZERO;
        BigDecimal weightSum = BigDecimal.ZERO;
        List<EvalRecordItem> saveItems = new ArrayList<>();

        for (Map<String, Object> item : items) {
            if (item.get("indicatorId") == null || item.get("score") == null) {
                throw new BusinessException("评分项参数不完整");
            }
            Long indicatorId = Long.valueOf(String.valueOf(item.get("indicatorId")));
            BigDecimal score = new BigDecimal(String.valueOf(item.get("score")));
            if (score.compareTo(BigDecimal.ZERO) < 0 || score.compareTo(BigDecimal.valueOf(100)) > 0) {
                throw new BusinessException("评分必须在0-100之间");
            }
            EvalIndicator indicator = indicatorMap.get(indicatorId);
            if (indicator == null) {
                throw new BusinessException("存在未启用或不存在的评价指标");
            }
            BigDecimal weight = indicator.getWeight() == null ? BigDecimal.ZERO : indicator.getWeight();
            weightedSum = weightedSum.add(score.multiply(weight));
            weightSum = weightSum.add(weight);

            EvalRecordItem saveItem = new EvalRecordItem();
            saveItem.setIndicatorId(indicatorId);
            saveItem.setScore(score);
            saveItems.add(saveItem);
        }

        if (weightSum.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("评价指标权重配置异常");
        }
        BigDecimal totalScore = weightedSum.divide(weightSum, 2, RoundingMode.HALF_UP);

        EvalRecord record = new EvalRecord();
        record.setTaskId(taskId);
        record.setCourseId(task.getCourseId());
        record.setStudentId(userId);
        record.setTotalScore(totalScore);
        record.setCommentContent(params.get("commentContent") == null ? null : String.valueOf(params.get("commentContent")));
        record.setSubmitTime(now);
        recordMapper.insert(record);

        for (EvalRecordItem saveItem : saveItems) {
            saveItem.setRecordId(record.getId());
        }
        recordItemMapper.insertBatch(saveItems);
    }

    public PageInfo<Map<String, Object>> taskRecords(Long taskId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Map<String, Object>> list = recordMapper.selectTaskRecordList(taskId);
        return new PageInfo<>(list);
    }

    public Map<String, Object> taskSummary(Long taskId) {
        List<Map<String, Object>> records = recordMapper.selectTaskRecordList(taskId);
        BigDecimal avgScore = BigDecimal.ZERO;
        if (!records.isEmpty()) {
            BigDecimal total = BigDecimal.ZERO;
            for (Map<String, Object> record : records) {
                Object value = record.get("totalScore");
                if (value == null) {
                    value = record.get("TOTAL_SCORE");
                }
                if (value != null) {
                    total = total.add(new BigDecimal(String.valueOf(value)));
                }
            }
            avgScore = total.divide(BigDecimal.valueOf(records.size()), 2, RoundingMode.HALF_UP);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("submitCount", records.size());
        result.put("avgScore", avgScore);
        result.put("indicatorAvg", recordItemMapper.selectIndicatorAvgByTask(taskId));
        return result;
    }
}
