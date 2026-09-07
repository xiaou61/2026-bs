<template>
  <DataPage title="减排任务" description="减排任务、目标减排量、责任人和执行状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReductionTaskPage, addReductionTask, updateReductionTask, deleteReductionTask, processReductionTask, finishReductionTask } from '../api'
const api = { page: getReductionTaskPage, add: addReductionTask, update: updateReductionTask, delete: deleteReductionTask }
const columns = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "taskName", "label": "任务名称"}, {"prop": "targetAmount", "label": "目标减排量"}, {"prop": "ownerName", "label": "责任人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "taskName", "label": "任务名称"}, {"prop": "targetAmount", "label": "目标减排量", "type": "number"}, {"prop": "ownerName", "label": "责任人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processReductionTask(row.id)
  if (command === 'finish') await finishReductionTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
