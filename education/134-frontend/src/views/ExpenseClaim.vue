<template>
  <DataPage title="报销申请" description="报销编号、项目编号、申请人、报销金额、申请时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getExpenseClaimPage, addExpenseClaim, updateExpenseClaim, deleteExpenseClaim, submitExpenseClaim, approveExpenseClaim } from '../api'
const api = { page: getExpenseClaimPage, add: addExpenseClaim, update: updateExpenseClaim, delete: deleteExpenseClaim }
const columns = [{"prop": "claimNo", "label": "报销编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "applicantName", "label": "申请人"}, {"prop": "claimAmount", "label": "报销金额"}, {"prop": "claimTime", "label": "申请时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "claimNo", "label": "报销编号"}, {"prop": "projectNo", "label": "项目编号"}, {"prop": "applicantName", "label": "申请人"}, {"prop": "claimAmount", "label": "报销金额", "type": "number"}, {"prop": "claimTime", "label": "申请时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitExpenseClaim(row.id)
  if (command === 'approve') await approveExpenseClaim(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
