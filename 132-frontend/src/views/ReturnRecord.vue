<template>
  <DataPage title="归还记录" description="归还编号、借用编号、归还时间、检查结果和接收人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReturnRecordPage, addReturnRecord, updateReturnRecord, deleteReturnRecord, submitReturnRecord, approveReturnRecord } from '../api'
const api = { page: getReturnRecordPage, add: addReturnRecord, update: updateReturnRecord, delete: deleteReturnRecord }
const columns = [{"prop": "returnNo", "label": "归还编号"}, {"prop": "borrowNo", "label": "借用编号"}, {"prop": "returnTime", "label": "归还时间"}, {"prop": "checkResult", "label": "检查结果"}, {"prop": "receiverName", "label": "接收人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "returnNo", "label": "归还编号"}, {"prop": "borrowNo", "label": "借用编号"}, {"prop": "returnTime", "label": "归还时间"}, {"prop": "checkResult", "label": "检查结果"}, {"prop": "receiverName", "label": "接收人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitReturnRecord(row.id)
  if (command === 'approve') await approveReturnRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
