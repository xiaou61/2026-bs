<template>
  <DataPage title="风险告警" description="异常访问、导出风险和高危字段告警处置" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRiskAlertPage, addRiskAlert, updateRiskAlert, deleteRiskAlert, ackRiskAlert, resolveRiskAlert, closeRiskAlert } from '../api'
const api = { page: getRiskAlertPage, add: addRiskAlert, update: updateRiskAlert, delete: deleteRiskAlert }
const columns = [{"prop": "alertNo", "label": "告警编号"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "alertType", "label": "告警类型"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "detectedTime", "label": "发现时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "alertNo", "label": "告警编号"}, {"prop": "datasetName", "label": "数据集"}, {"prop": "alertType", "label": "告警类型", "type": "select", "options": [{"label": "RAW_EXPORT", "value": "RAW_EXPORT"}, {"label": "HIGH_RISK_FIELD", "value": "HIGH_RISK_FIELD"}, {"label": "OVERDUE_ACCESS", "value": "OVERDUE_ACCESS"}, {"label": "SCAN_FAILED", "value": "SCAN_FAILED"}]}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}, {"label": "CRITICAL", "value": "CRITICAL"}]}, {"prop": "ownerName", "label": "负责人"}, {"prop": "detectedTime", "label": "发现时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "ACKED", "value": "ACKED"}, {"label": "RESOLVED", "value": "RESOLVED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "ack", "label": "确认", "type": "primary"}, {"command": "resolve", "label": "解决", "type": "primary"}, {"command": "close", "label": "关闭", "type": "primary"}]
const defaults = {"riskLevel": "HIGH", "status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'ack') await ackRiskAlert(row.id)
  if (command === 'resolve') await resolveRiskAlert(row.id)
  if (command === 'close') await closeRiskAlert(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
