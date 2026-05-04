<template>
  <DataPage title="成本账单" description="云账号账期、应付实付和支付状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCostBillPage, addCostBill, updateCostBill, deleteCostBill, confirmCostBill, payCostBill, closeCostBill } from '../api'
const api = { page: getCostBillPage, add: addCostBill, update: updateCostBill, delete: deleteCostBill }
const columns = [{"prop": "billNo", "label": "账单编号"}, {"prop": "accountName", "label": "云账号"}, {"prop": "billMonth", "label": "账期"}, {"prop": "currency", "label": "币种"}, {"prop": "totalAmount", "label": "账单金额"}, {"prop": "payAmount", "label": "实付金额"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "billNo", "label": "账单编号"}, {"prop": "accountName", "label": "云账号"}, {"prop": "billMonth", "label": "账期"}, {"prop": "currency", "label": "币种", "type": "select", "options": [{"label": "CNY", "value": "CNY"}, {"label": "USD", "value": "USD"}]}, {"prop": "totalAmount", "label": "账单金额", "type": "number"}, {"prop": "payAmount", "label": "实付金额", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_CONFIRM", "value": "WAIT_CONFIRM"}, {"label": "CONFIRMED", "value": "CONFIRMED"}, {"label": "PAID", "value": "PAID"}, {"label": "CLOSED", "value": "CLOSED"}]}]
const rowActions = [{"command": "confirm", "label": "确认", "type": "primary"}, {"command": "pay", "label": "支付", "type": "primary"}, {"command": "close", "label": "关闭", "type": "primary"}]
const defaults = {"currency": "CNY", "status": "WAIT_CONFIRM"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'confirm') await confirmCostBill(row.id)
  if (command === 'pay') await payCostBill(row.id)
  if (command === 'close') await closeCostBill(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
