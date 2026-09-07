<template>
  <DataPage title="借用申请" description="申请编号、器械、申请科室、申请人、申请时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getBorrowRequestPage, addBorrowRequest, updateBorrowRequest, deleteBorrowRequest, submitBorrowRequest, approveBorrowRequest } from '../api'
const api = { page: getBorrowRequestPage, add: addBorrowRequest, update: updateBorrowRequest, delete: deleteBorrowRequest }
const columns = [{"prop": "requestNo", "label": "申请编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "deptName", "label": "申请科室"}, {"prop": "applicantName", "label": "申请人"}, {"prop": "requestTime", "label": "申请时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "requestNo", "label": "申请编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "deptName", "label": "申请科室"}, {"prop": "applicantName", "label": "申请人"}, {"prop": "requestTime", "label": "申请时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitBorrowRequest(row.id)
  if (command === 'approve') await approveBorrowRequest(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
