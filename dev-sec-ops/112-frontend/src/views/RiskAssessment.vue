<template>
  <DataPage title="风险评估" description="设备身份访问前风险评分和处置建议管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRiskAssessmentPage, addRiskAssessment, updateRiskAssessment, deleteRiskAssessment, reviewRiskAssessment, clearRiskAssessment, blockRiskAssessment } from '../api'
const api = { page: getRiskAssessmentPage, add: addRiskAssessment, update: updateRiskAssessment, delete: deleteRiskAssessment }
const columns = [{"prop": "assessmentNo", "label": "评估编号"}, {"prop": "deviceName", "label": "设备"}, {"prop": "employeeName", "label": "员工"}, {"prop": "riskScore", "label": "风险分"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "adviceText", "label": "建议"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "assessmentNo", "label": "评估编号"}, {"prop": "deviceName", "label": "设备"}, {"prop": "employeeName", "label": "员工"}, {"prop": "riskScore", "label": "风险分", "type": "number"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}, {"label": "CRITICAL", "value": "CRITICAL"}]}, {"prop": "adviceText", "label": "建议", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_REVIEW", "value": "WAIT_REVIEW"}, {"label": "REVIEWED", "value": "REVIEWED"}, {"label": "CLEARED", "value": "CLEARED"}, {"label": "BLOCKED", "value": "BLOCKED"}]}]
const rowActions = [{"command": "review", "label": "复核", "type": "primary"}, {"command": "clear", "label": "放行", "type": "primary"}, {"command": "block", "label": "阻断", "type": "primary"}]
const defaults = {"riskLevel": "MEDIUM", "status": "WAIT_REVIEW"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'review') await reviewRiskAssessment(row.id)
  if (command === 'clear') await clearRiskAssessment(row.id)
  if (command === 'block') await blockRiskAssessment(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
