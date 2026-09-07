<template>
  <DataPage title="退款记录" description="退款单、售后工单、金额、原因、审核人和退款状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRefundRecordPage, addRefundRecord, updateRefundRecord, deleteRefundRecord, approveRefundRecord, payRefundRecord, rejectRefundRecord } from '../api'
const api = { page: getRefundRecordPage, add: addRefundRecord, update: updateRefundRecord, delete: deleteRefundRecord }
const columns = [{"prop": "refundNo", "label": "退款编号"}, {"prop": "ticketNo", "label": "工单编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "refundAmount", "label": "退款金额"}, {"prop": "reasonText", "label": "退款原因"}, {"prop": "auditUser", "label": "审核人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "refundNo", "label": "退款编号"}, {"prop": "ticketNo", "label": "工单编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "refundAmount", "label": "退款金额", "type": "number"}, {"prop": "reasonText", "label": "退款原因", "type": "textarea"}, {"prop": "auditUser", "label": "审核人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_AUDIT", "value": "WAIT_AUDIT"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "PAID", "value": "PAID"}, {"label": "REJECTED", "value": "REJECTED"}]}]
const rowActions = [{"command": "approve", "label": "通过", "type": "success"}, {"command": "pay", "label": "打款", "type": "success"}, {"command": "reject", "label": "拒绝", "type": "danger"}]
const defaults = {"status": "WAIT_AUDIT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'approve') await approveRefundRecord(row.id)
  if (command === 'pay') await payRefundRecord(row.id)
  if (command === 'reject') await rejectRefundRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
