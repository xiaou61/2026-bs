<template>
  <DataPage title="支付记录" description="支付编号、停车编号、车主、金额、支付方式和支付时间管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPaymentRecordPage, addPaymentRecord, updatePaymentRecord, deletePaymentRecord, refundPaymentRecord, finishPaymentRecord } from '../api'
const api = { page: getPaymentRecordPage, add: addPaymentRecord, update: updatePaymentRecord, delete: deletePaymentRecord }
const columns = [{"prop": "paymentNo", "label": "支付编号"}, {"prop": "recordNo", "label": "停车编号"}, {"prop": "ownerName", "label": "车主"}, {"prop": "payAmount", "label": "支付金额"}, {"prop": "payMethod", "label": "支付方式"}, {"prop": "payTime", "label": "支付时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "paymentNo", "label": "支付编号"}, {"prop": "recordNo", "label": "停车编号"}, {"prop": "ownerName", "label": "车主"}, {"prop": "payAmount", "label": "支付金额", "type": "number"}, {"prop": "payMethod", "label": "支付方式"}, {"prop": "payTime", "label": "支付时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "refund", "label": "退款", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "PAID"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'refund') await refundPaymentRecord(row.id)
  if (command === 'finish') await finishPaymentRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
