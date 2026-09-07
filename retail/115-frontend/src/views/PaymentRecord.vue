<template>
  <DataPage title="支付记录" description="结算支付流水、支付渠道、金额、币种、时间和支付状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPaymentRecordPage, addPaymentRecord, updatePaymentRecord, deletePaymentRecord, confirmPaymentRecord, failPaymentRecord } from '../api'
const api = { page: getPaymentRecordPage, add: addPaymentRecord, update: updatePaymentRecord, delete: deletePaymentRecord }
const columns = [{"prop": "paymentNo", "label": "支付编号"}, {"prop": "billNo", "label": "账单编号"}, {"prop": "payChannel", "label": "支付渠道"}, {"prop": "payAmount", "label": "支付金额"}, {"prop": "currencyCode", "label": "币种"}, {"prop": "payTime", "label": "支付时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "paymentNo", "label": "支付编号"}, {"prop": "billNo", "label": "账单编号"}, {"prop": "payChannel", "label": "支付渠道", "type": "select", "options": [{"label": "BANK_TRANSFER", "value": "BANK_TRANSFER"}, {"label": "PAYPAL", "value": "PAYPAL"}, {"label": "STRIPE", "value": "STRIPE"}, {"label": "ALIPAY", "value": "ALIPAY"}]}, {"prop": "payAmount", "label": "支付金额", "type": "number"}, {"prop": "currencyCode", "label": "币种", "type": "select", "options": [{"label": "CNY", "value": "CNY"}, {"label": "USD", "value": "USD"}, {"label": "EUR", "value": "EUR"}]}, {"prop": "payTime", "label": "支付时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_CONFIRM", "value": "WAIT_CONFIRM"}, {"label": "SUCCESS", "value": "SUCCESS"}, {"label": "FAILED", "value": "FAILED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "success"}, {"command": "fail", "label": "失败", "type": "danger"}]
const defaults = {"currencyCode": "CNY", "status": "WAIT_CONFIRM"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmPaymentRecord(row.id)
  if (command === 'fail') await failPaymentRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
