<template>
  <DataPage title="风险预警" description="过期访问、无授权访问和高频访问风险预警处置" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRiskWarningPage, addRiskWarning, updateRiskWarning, deleteRiskWarning, ackRiskWarning, resolveRiskWarning, closeRiskWarning } from '../api'
const api = { page: getRiskWarningPage, add: addRiskWarning, update: updateRiskWarning, delete: deleteRiskWarning }
const columns = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "warningType", "label": "预警类型"}, {"prop": "subjectName", "label": "数据主体"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "detectedTime", "label": "发现时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "warningType", "label": "预警类型", "type": "select", "options": [{"label": "EXPIRED_ACCESS", "value": "EXPIRED_ACCESS"}, {"label": "NO_AUTH", "value": "NO_AUTH"}, {"label": "HIGH_FREQUENCY", "value": "HIGH_FREQUENCY"}, {"label": "EXPORT_RISK", "value": "EXPORT_RISK"}]}, {"prop": "subjectName", "label": "数据主体"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}, {"label": "CRITICAL", "value": "CRITICAL"}]}, {"prop": "ownerName", "label": "负责人"}, {"prop": "detectedTime", "label": "发现时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "ACKED", "value": "ACKED"}, {"label": "RESOLVED", "value": "RESOLVED"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "ack", "label": "确认", "type": "primary"}, {"command": "resolve", "label": "解决", "type": "primary"}, {"command": "close", "label": "关闭", "type": "primary"}]
const defaults = {"riskLevel": "HIGH", "status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'ack') await ackRiskWarning(row.id)
  if (command === 'resolve') await resolveRiskWarning(row.id)
  if (command === 'close') await closeRiskWarning(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
