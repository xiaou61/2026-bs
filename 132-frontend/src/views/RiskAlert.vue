<template>
  <DataPage title="风险预警" description="预警编号、器械、预警等级、触发原因、处理人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRiskAlertPage, addRiskAlert, updateRiskAlert, deleteRiskAlert, processRiskAlert, finishRiskAlert } from '../api'
const api = { page: getRiskAlertPage, add: addRiskAlert, update: updateRiskAlert, delete: deleteRiskAlert }
const columns = [{"prop": "alertNo", "label": "预警编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "alertLevel", "label": "预警等级"}, {"prop": "triggerReason", "label": "触发原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "alertNo", "label": "预警编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "alertLevel", "label": "预警等级"}, {"prop": "triggerReason", "label": "触发原因"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processRiskAlert(row.id)
  if (command === 'finish') await finishRiskAlert(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
