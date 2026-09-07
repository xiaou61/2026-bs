<template>
  <DataPage title="借用记录" description="借用编号、申请编号、器械、借出时间、借用人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getBorrowRecordPage, addBorrowRecord, updateBorrowRecord, deleteBorrowRecord, processBorrowRecord, finishBorrowRecord } from '../api'
const api = { page: getBorrowRecordPage, add: addBorrowRecord, update: updateBorrowRecord, delete: deleteBorrowRecord }
const columns = [{"prop": "borrowNo", "label": "借用编号"}, {"prop": "requestNo", "label": "申请编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "borrowTime", "label": "借出时间"}, {"prop": "borrowerName", "label": "借用人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "borrowNo", "label": "借用编号"}, {"prop": "requestNo", "label": "申请编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "borrowTime", "label": "借出时间"}, {"prop": "borrowerName", "label": "借用人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processBorrowRecord(row.id)
  if (command === 'finish') await finishBorrowRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
