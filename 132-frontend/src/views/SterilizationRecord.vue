<template>
  <DataPage title="消毒记录" description="记录编号、批次、器械、消毒结果、操作人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSterilizationRecordPage, addSterilizationRecord, updateSterilizationRecord, deleteSterilizationRecord, submitSterilizationRecord, approveSterilizationRecord } from '../api'
const api = { page: getSterilizationRecordPage, add: addSterilizationRecord, update: updateSterilizationRecord, delete: deleteSterilizationRecord }
const columns = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "sterilizeResult", "label": "消毒结果"}, {"prop": "operatorName", "label": "操作人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "sterilizeResult", "label": "消毒结果"}, {"prop": "operatorName", "label": "操作人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitSterilizationRecord(row.id)
  if (command === 'approve') await approveSterilizationRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
