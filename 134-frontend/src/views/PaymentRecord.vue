<template>
  <DataPage title="支付记录" description="支付编号、报销编号、支付金额、支付时间、经办人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPaymentRecordPage, addPaymentRecord, updatePaymentRecord, deletePaymentRecord, processPaymentRecord, finishPaymentRecord } from '../api'
const api = { page: getPaymentRecordPage, add: addPaymentRecord, update: updatePaymentRecord, delete: deletePaymentRecord }
const columns = [{"prop": "paymentNo", "label": "支付编号"}, {"prop": "claimNo", "label": "报销编号"}, {"prop": "paymentAmount", "label": "支付金额"}, {"prop": "paymentTime", "label": "支付时间"}, {"prop": "operatorName", "label": "经办人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "paymentNo", "label": "支付编号"}, {"prop": "claimNo", "label": "报销编号"}, {"prop": "paymentAmount", "label": "支付金额", "type": "number"}, {"prop": "paymentTime", "label": "支付时间"}, {"prop": "operatorName", "label": "经办人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "FINISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processPaymentRecord(row.id)
  if (command === 'finish') await finishPaymentRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
