<template>
  <DataPage title="核算周期" description="核算周期、企业边界、核算月份和复核人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAccountingPeriodPage, addAccountingPeriod, updateAccountingPeriod, deleteAccountingPeriod, submitAccountingPeriod, approveAccountingPeriod } from '../api'
const api = { page: getAccountingPeriodPage, add: addAccountingPeriod, update: updateAccountingPeriod, delete: deleteAccountingPeriod }
const columns = [{"prop": "periodNo", "label": "周期编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "periodMonth", "label": "核算月份"}, {"prop": "boundaryScope", "label": "核算边界"}, {"prop": "reviewerName", "label": "复核人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "periodNo", "label": "周期编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "periodMonth", "label": "核算月份"}, {"prop": "boundaryScope", "label": "核算边界"}, {"prop": "reviewerName", "label": "复核人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitAccountingPeriod(row.id)
  if (command === 'approve') await approveAccountingPeriod(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
