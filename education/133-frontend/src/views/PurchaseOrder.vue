<template>
  <DataPage title="采购订单" description="订单编号、供应商、耗材名称、订单金额、预计到货日期和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getPurchaseOrderPage, addPurchaseOrder, updatePurchaseOrder, deletePurchaseOrder, processPurchaseOrder, finishPurchaseOrder } from '../api'
const api = { page: getPurchaseOrderPage, add: addPurchaseOrder, update: updatePurchaseOrder, delete: deletePurchaseOrder }
const columns = [{"prop": "orderNo", "label": "订单编号"}, {"prop": "supplierName", "label": "供应商"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "orderAmount", "label": "订单金额"}, {"prop": "arrivalDate", "label": "预计到货日期"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "orderNo", "label": "订单编号"}, {"prop": "supplierName", "label": "供应商"}, {"prop": "consumableName", "label": "耗材名称"}, {"prop": "orderAmount", "label": "订单金额", "type": "number"}, {"prop": "arrivalDate", "label": "预计到货日期"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processPurchaseOrder(row.id)
  if (command === 'finish') await finishPurchaseOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
