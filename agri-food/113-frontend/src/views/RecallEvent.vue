<template>
  <DataPage title="召回事件" description="问题批次召回原因、影响范围和处置进度管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRecallEventPage, addRecallEvent, updateRecallEvent, deleteRecallEvent, processRecallEvent, closeRecallEvent } from '../api'
const api = { page: getRecallEventPage, add: addRecallEvent, update: updateRecallEvent, delete: deleteRecallEvent }
const columns = [{"prop": "recallNo", "label": "召回编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "productName", "label": "产品名称"}, {"prop": "reasonText", "label": "召回原因"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recallNo", "label": "召回编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "productName", "label": "产品名称"}, {"prop": "reasonText", "label": "召回原因", "type": "textarea"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}, {"label": "CRITICAL", "value": "CRITICAL"}]}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "process", "label": "处理中", "type": "primary"}, {"command": "close", "label": "关闭", "type": "primary"}]
const defaults = {"riskLevel": "MEDIUM", "status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processRecallEvent(row.id)
  if (command === 'close') await closeRecallEvent(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
