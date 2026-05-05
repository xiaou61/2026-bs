<template>
  <DataPage title="处置建议" description="建议编号、上报编号、建议类型、建议内容和医生维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTreatmentAdvicePage, addTreatmentAdvice, updateTreatmentAdvice, deleteTreatmentAdvice, activateTreatmentAdvice, finishTreatmentAdvice } from '../api'
const api = { page: getTreatmentAdvicePage, add: addTreatmentAdvice, update: updateTreatmentAdvice, delete: deleteTreatmentAdvice }
const columns = [{"prop": "adviceNo", "label": "建议编号"}, {"prop": "reportNo", "label": "上报编号"}, {"prop": "adviceType", "label": "建议类型"}, {"prop": "adviceContent", "label": "建议内容"}, {"prop": "doctorName", "label": "医生"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "adviceNo", "label": "建议编号"}, {"prop": "reportNo", "label": "上报编号"}, {"prop": "adviceType", "label": "建议类型"}, {"prop": "adviceContent", "label": "建议内容"}, {"prop": "doctorName", "label": "医生"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "PUBLISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateTreatmentAdvice(row.id)
  if (command === 'finish') await finishTreatmentAdvice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
