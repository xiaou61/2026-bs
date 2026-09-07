<template>
  <DataPage title="风险预警" description="预警编号、项目编号、风险级别、触发原因、处理人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRiskWarningPage, addRiskWarning, updateRiskWarning, deleteRiskWarning, processRiskWarning, finishRiskWarning } from '../api'
const api = { page: getRiskWarningPage, add: addRiskWarning, update: updateRiskWarning, delete: deleteRiskWarning }
const columns = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "riskLevel", "label": "风险级别"}, {"prop": "triggerReason", "label": "触发原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "warningNo", "label": "预警编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "riskLevel", "label": "风险级别"}, {"prop": "triggerReason", "label": "触发原因", "type": "textarea"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "WARNING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processRiskWarning(row.id)
  if (command === 'finish') await finishRiskWarning(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
