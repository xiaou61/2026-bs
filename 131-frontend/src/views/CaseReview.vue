<template>
  <DataPage title="病例复核" description="复核编号、上报编号、复核人、复核意见和复核时间维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCaseReviewPage, addCaseReview, updateCaseReview, deleteCaseReview, submitCaseReview, approveCaseReview } from '../api'
const api = { page: getCaseReviewPage, add: addCaseReview, update: updateCaseReview, delete: deleteCaseReview }
const columns = [{"prop": "reviewNo", "label": "复核编号"}, {"prop": "reportNo", "label": "上报编号"}, {"prop": "reviewerName", "label": "复核人"}, {"prop": "reviewOpinion", "label": "复核意见"}, {"prop": "reviewTime", "label": "复核时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "reviewNo", "label": "复核编号"}, {"prop": "reportNo", "label": "上报编号"}, {"prop": "reviewerName", "label": "复核人"}, {"prop": "reviewOpinion", "label": "复核意见"}, {"prop": "reviewTime", "label": "复核时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "REVIEWING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitCaseReview(row.id)
  if (command === 'approve') await approveCaseReview(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
