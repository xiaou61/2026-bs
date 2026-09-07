<template>
  <DataPage title="发票记录" description="发票编号、报销编号、发票类型、发票金额、开票单位和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInvoiceRecordPage, addInvoiceRecord, updateInvoiceRecord, deleteInvoiceRecord, submitInvoiceRecord, approveInvoiceRecord } from '../api'
const api = { page: getInvoiceRecordPage, add: addInvoiceRecord, update: updateInvoiceRecord, delete: deleteInvoiceRecord }
const columns = [{"prop": "invoiceNo", "label": "发票编号"}, {"prop": "claimNo", "label": "报销编号"}, {"prop": "invoiceType", "label": "发票类型"}, {"prop": "invoiceAmount", "label": "发票金额"}, {"prop": "issuerName", "label": "开票单位"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "invoiceNo", "label": "发票编号"}, {"prop": "claimNo", "label": "报销编号"}, {"prop": "invoiceType", "label": "发票类型"}, {"prop": "invoiceAmount", "label": "发票金额", "type": "number"}, {"prop": "issuerName", "label": "开票单位"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitInvoiceRecord(row.id)
  if (command === 'approve') await approveInvoiceRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
