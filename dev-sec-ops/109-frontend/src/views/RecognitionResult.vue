<template>
  <DataPage title="识别结果" description="字段命中类型、样例值和置信度复核管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRecognitionResultPage, addRecognitionResult, updateRecognitionResult, deleteRecognitionResult, confirmRecognitionResult, ignoreRecognitionResult } from '../api'
const api = { page: getRecognitionResultPage, add: addRecognitionResult, update: updateRecognitionResult, delete: deleteRecognitionResult }
const columns = [{"prop": "resultNo", "label": "结果编号"}, {"prop": "taskNo", "label": "任务编号"}, {"prop": "fieldName", "label": "字段名"}, {"prop": "sensitiveType", "label": "敏感类型"}, {"prop": "sampleValue", "label": "样例值"}, {"prop": "confidence", "label": "置信度"}, {"prop": "riskLevel", "label": "风险等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "resultNo", "label": "结果编号"}, {"prop": "taskNo", "label": "任务编号"}, {"prop": "fieldName", "label": "字段名"}, {"prop": "sensitiveType", "label": "敏感类型", "type": "select", "options": [{"label": "ID_CARD", "value": "ID_CARD"}, {"label": "PHONE", "value": "PHONE"}, {"label": "EMAIL", "value": "EMAIL"}, {"label": "BANK_CARD", "value": "BANK_CARD"}, {"label": "ADDRESS", "value": "ADDRESS"}]}, {"prop": "sampleValue", "label": "样例值"}, {"prop": "confidence", "label": "置信度", "type": "number"}, {"prop": "riskLevel", "label": "风险等级", "type": "select", "options": [{"label": "LOW", "value": "LOW"}, {"label": "MEDIUM", "value": "MEDIUM"}, {"label": "HIGH", "value": "HIGH"}, {"label": "CRITICAL", "value": "CRITICAL"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "PENDING", "value": "PENDING"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "IGNORED", "value": "IGNORED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "primary"}, {"command": "ignore", "label": "忽略", "type": "primary"}]
const defaults = {"status": "PENDING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmRecognitionResult(row.id)
  if (command === 'ignore') await ignoreRecognitionResult(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
