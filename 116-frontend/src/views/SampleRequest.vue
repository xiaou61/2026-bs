<template>
  <DataPage title="样品申请" description="样品申请、商品、供应商、收件人、快递单号和收样状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSampleRequestPage, addSampleRequest, updateSampleRequest, deleteSampleRequest, sendSampleRequest, receiveSampleRequest } from '../api'
const api = { page: getSampleRequestPage, add: addSampleRequest, update: updateSampleRequest, delete: deleteSampleRequest }
const columns = [{"prop": "requestNo", "label": "申请编号"}, {"prop": "productName", "label": "商品"}, {"prop": "supplierName", "label": "供应商"}, {"prop": "receiverName", "label": "收件人"}, {"prop": "expressNo", "label": "快递单号"}, {"prop": "requestDate", "label": "申请日期"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "requestNo", "label": "申请编号"}, {"prop": "productName", "label": "商品"}, {"prop": "supplierName", "label": "供应商"}, {"prop": "receiverName", "label": "收件人"}, {"prop": "expressNo", "label": "快递单号"}, {"prop": "requestDate", "label": "申请日期"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "APPLIED", "value": "APPLIED"}, {"label": "SENT", "value": "SENT"}, {"label": "RECEIVED", "value": "RECEIVED"}]}]
const rowActions = [{"command": "send", "label": "寄出", "type": "primary"}, {"command": "receive", "label": "收样", "type": "success"}]
const defaults = {"status": "APPLIED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'send') await sendSampleRequest(row.id)
  if (command === 'receive') await receiveSampleRequest(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
