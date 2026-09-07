<template>
  <DataPage title="税费记录" description="关税、增值税、消费税、缴纳金额、币种和支付状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTaxFeeRecordPage, addTaxFeeRecord, updateTaxFeeRecord, deleteTaxFeeRecord, confirmTaxFeeRecord, overdueTaxFeeRecord } from '../api'
const api = { page: getTaxFeeRecordPage, add: addTaxFeeRecord, update: updateTaxFeeRecord, delete: deleteTaxFeeRecord }
const columns = [{"prop": "feeNo", "label": "税费编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "taxType", "label": "税种"}, {"prop": "taxAmount", "label": "税费金额"}, {"prop": "currencyCode", "label": "币种"}, {"prop": "payStatus", "label": "支付状态"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "feeNo", "label": "税费编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "taxType", "label": "税种", "type": "select", "options": [{"label": "DUTY", "value": "DUTY"}, {"label": "VAT", "value": "VAT"}, {"label": "CONSUMPTION", "value": "CONSUMPTION"}]}, {"prop": "taxAmount", "label": "税费金额", "type": "number"}, {"prop": "currencyCode", "label": "币种", "type": "select", "options": [{"label": "USD", "value": "USD"}, {"label": "EUR", "value": "EUR"}, {"label": "JPY", "value": "JPY"}, {"label": "CNY", "value": "CNY"}]}, {"prop": "payStatus", "label": "支付状态", "type": "select", "options": [{"label": "WAIT_PAY", "value": "WAIT_PAY"}, {"label": "PAID", "value": "PAID"}, {"label": "OVERDUE", "value": "OVERDUE"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_PAY", "value": "WAIT_PAY"}, {"label": "PAID", "value": "PAID"}, {"label": "OVERDUE", "value": "OVERDUE"}]}]
const rowActions = [{"command": "confirm", "label": "确认缴纳", "type": "success"}, {"command": "overdue", "label": "逾期", "type": "danger"}]
const defaults = {"currencyCode": "USD", "payStatus": "WAIT_PAY", "status": "WAIT_PAY"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmTaxFeeRecord(row.id)
  if (command === 'overdue') await overdueTaxFeeRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
