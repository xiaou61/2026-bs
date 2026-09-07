<template>
  <DataPage title="审核任务" description="审核任务、填报编号、审核员、截止时间和审核状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReviewTaskPage, addReviewTask, updateReviewTask, deleteReviewTask, submitReviewTask, approveReviewTask } from '../api'
const api = { page: getReviewTaskPage, add: addReviewTask, update: updateReviewTask, delete: deleteReviewTask }
const columns = [{"prop": "reviewNo", "label": "审核编号"}, {"prop": "submissionNo", "label": "填报编号"}, {"prop": "reviewerName", "label": "审核员"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "reviewOpinion", "label": "审核意见"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "reviewNo", "label": "审核编号"}, {"prop": "submissionNo", "label": "填报编号"}, {"prop": "reviewerName", "label": "审核员"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "reviewOpinion", "label": "审核意见"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "REVIEWING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitReviewTask(row.id)
  if (command === 'approve') await approveReviewTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
