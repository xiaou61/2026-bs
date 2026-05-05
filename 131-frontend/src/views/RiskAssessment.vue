<template>
  <DataPage title="风险评估" description="评估编号、上报编号、风险等级、评分和评估人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRiskAssessmentPage, addRiskAssessment, updateRiskAssessment, deleteRiskAssessment, submitRiskAssessment, approveRiskAssessment } from '../api'
const api = { page: getRiskAssessmentPage, add: addRiskAssessment, update: updateRiskAssessment, delete: deleteRiskAssessment }
const columns = [{"prop": "assessNo", "label": "评估编号"}, {"prop": "reportNo", "label": "上报编号"}, {"prop": "assessLevel", "label": "风险等级"}, {"prop": "scoreValue", "label": "评分"}, {"prop": "assessorName", "label": "评估人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "assessNo", "label": "评估编号"}, {"prop": "reportNo", "label": "上报编号"}, {"prop": "assessLevel", "label": "风险等级"}, {"prop": "scoreValue", "label": "评分", "type": "number"}, {"prop": "assessorName", "label": "评估人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "REVIEWING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitRiskAssessment(row.id)
  if (command === 'approve') await approveRiskAssessment(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
