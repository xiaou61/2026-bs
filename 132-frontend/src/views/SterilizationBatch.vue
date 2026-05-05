<template>
  <DataPage title="消毒批次" description="批次编号、消毒方式、设备、负责人、开始时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSterilizationBatchPage, addSterilizationBatch, updateSterilizationBatch, deleteSterilizationBatch, processSterilizationBatch, finishSterilizationBatch } from '../api'
const api = { page: getSterilizationBatchPage, add: addSterilizationBatch, update: updateSterilizationBatch, delete: deleteSterilizationBatch }
const columns = [{"prop": "batchNo", "label": "批次编号"}, {"prop": "sterilizeMethod", "label": "消毒方式"}, {"prop": "machineName", "label": "消毒设备"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "startTime", "label": "开始时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "batchNo", "label": "批次编号"}, {"prop": "sterilizeMethod", "label": "消毒方式"}, {"prop": "machineName", "label": "消毒设备"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "startTime", "label": "开始时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processSterilizationBatch(row.id)
  if (command === 'finish') await finishSterilizationBatch(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
