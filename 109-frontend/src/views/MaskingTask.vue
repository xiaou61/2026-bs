<template>
  <DataPage title="脱敏任务" description="数据集脱敏任务、执行模式和运行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaskingTaskPage, addMaskingTask, updateMaskingTask, deleteMaskingTask, submitMaskingTask, startMaskingTask, finishMaskingTask, failMaskingTask } from '../api'
const api = { page: getMaskingTaskPage, add: addMaskingTask, update: updateMaskingTask, delete: deleteMaskingTask }
const columns = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "strategyName", "label": "策略"}, {"prop": "executeMode", "label": "执行模式"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "startedTime", "label": "开始时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "strategyName", "label": "策略"}, {"prop": "executeMode", "label": "执行模式", "type": "select", "options": [{"label": "MANUAL", "value": "MANUAL"}, {"label": "SCHEDULED", "value": "SCHEDULED"}, {"label": "API", "value": "API"}]}, {"prop": "ownerName", "label": "负责人"}, {"prop": "startedTime", "label": "开始时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "RUNNING", "value": "RUNNING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "FAILED", "value": "FAILED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "start", "label": "启动", "type": "primary"}, {"command": "finish", "label": "完成", "type": "primary"}, {"command": "fail", "label": "失败", "type": "primary"}]
const defaults = {"executeMode": "MANUAL", "status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitMaskingTask(row.id)
  if (command === 'start') await startMaskingTask(row.id)
  if (command === 'finish') await finishMaskingTask(row.id)
  if (command === 'fail') await failMaskingTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
