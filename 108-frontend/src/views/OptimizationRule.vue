<template>
  <DataPage title="优化规则" description="指标阈值、资源类型和优化动作规则管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getOptimizationRulePage, addOptimizationRule, updateOptimizationRule, deleteOptimizationRule, enableOptimizationRule, pauseOptimizationRule } from '../api'
const api = { page: getOptimizationRulePage, add: addOptimizationRule, update: updateOptimizationRule, delete: deleteOptimizationRule }
const columns = [{"prop": "ruleName", "label": "规则名称"}, {"prop": "resourceType", "label": "资源类型"}, {"prop": "metricName", "label": "指标名称"}, {"prop": "thresholdValue", "label": "阈值"}, {"prop": "actionType", "label": "动作类型"}, {"prop": "priority", "label": "优先级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "ruleName", "label": "规则名称"}, {"prop": "resourceType", "label": "资源类型", "type": "select", "options": [{"label": "ECS", "value": "ECS"}, {"label": "DISK", "value": "DISK"}, {"label": "RDS", "value": "RDS"}, {"label": "OSS", "value": "OSS"}]}, {"prop": "metricName", "label": "指标名称"}, {"prop": "thresholdValue", "label": "阈值", "type": "number"}, {"prop": "actionType", "label": "动作类型", "type": "select", "options": [{"label": "DOWNSIZE", "value": "DOWNSIZE"}, {"label": "CLEAN", "value": "CLEAN"}, {"label": "RESERVE", "value": "RESERVE"}, {"label": "MERGE", "value": "MERGE"}]}, {"prop": "priority", "label": "优先级", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "PAUSED", "value": "PAUSED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "primary"}, {"command": "pause", "label": "暂停", "type": "primary"}]
const defaults = {"resourceType": "ECS", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableOptimizationRule(row.id)
  if (command === 'pause') await pauseOptimizationRule(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
