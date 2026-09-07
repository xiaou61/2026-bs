<template>
  <DataPage title="利益相关方反馈" description="反馈编号、相关方、反馈类型、反馈内容和处理状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getStakeholderFeedbackPage, addStakeholderFeedback, updateStakeholderFeedback, deleteStakeholderFeedback, processStakeholderFeedback, finishStakeholderFeedback } from '../api'
const api = { page: getStakeholderFeedbackPage, add: addStakeholderFeedback, update: updateStakeholderFeedback, delete: deleteStakeholderFeedback }
const columns = [{"prop": "feedbackNo", "label": "反馈编号"}, {"prop": "stakeholderName", "label": "相关方"}, {"prop": "feedbackType", "label": "反馈类型"}, {"prop": "contentText", "label": "反馈内容"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "feedbackNo", "label": "反馈编号"}, {"prop": "stakeholderName", "label": "相关方"}, {"prop": "feedbackType", "label": "反馈类型"}, {"prop": "contentText", "label": "反馈内容"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processStakeholderFeedback(row.id)
  if (command === 'finish') await finishStakeholderFeedback(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
