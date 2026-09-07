<template>
  <DataPage title="投喂记录" description="投喂编号、池塘、投喂时间、投喂量和操作人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFeedingRecordPage, addFeedingRecord, updateFeedingRecord, deleteFeedingRecord, submitFeedingRecord, approveFeedingRecord } from '../api'
const api = { page: getFeedingRecordPage, add: addFeedingRecord, update: updateFeedingRecord, delete: deleteFeedingRecord }
const columns = [{"prop": "feedNo", "label": "投喂编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "feedTime", "label": "投喂时间"}, {"prop": "feedAmount", "label": "投喂量"}, {"prop": "operatorName", "label": "操作人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "feedNo", "label": "投喂编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "feedTime", "label": "投喂时间"}, {"prop": "feedAmount", "label": "投喂量", "type": "number"}, {"prop": "operatorName", "label": "操作人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitFeedingRecord(row.id)
  if (command === 'approve') await approveFeedingRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
