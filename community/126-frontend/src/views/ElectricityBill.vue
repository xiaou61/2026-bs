<template>
  <DataPage title="电费账单" description="账单编号、家庭、月份、用电量、电费金额和支付状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getElectricityBillPage, addElectricityBill, updateElectricityBill, deleteElectricityBill, payElectricityBill, closeElectricityBill } from '../api'
const api = { page: getElectricityBillPage, add: addElectricityBill, update: updateElectricityBill, delete: deleteElectricityBill }
const columns = [{"prop": "billNo", "label": "账单编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "billMonth", "label": "账单月份"}, {"prop": "energyKwh", "label": "用电量"}, {"prop": "billAmount", "label": "账单金额"}, {"prop": "payMethod", "label": "支付方式"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "billNo", "label": "账单编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "billMonth", "label": "账单月份"}, {"prop": "energyKwh", "label": "用电量", "type": "number"}, {"prop": "billAmount", "label": "账单金额", "type": "number"}, {"prop": "payMethod", "label": "支付方式"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "pay", "label": "支付", "type": "success"}, {"command": "close", "label": "关闭", "type": "info"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'pay') await payElectricityBill(row.id)
  if (command === 'close') await closeElectricityBill(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
