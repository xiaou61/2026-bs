<template>
  <DataPage title="跨境订单" description="跨境订单、店铺、客户、目的国、币种金额和履约状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCrossBorderOrderPage, addCrossBorderOrder, updateCrossBorderOrder, deleteCrossBorderOrder, submitCrossBorderOrder, shipCrossBorderOrder, closeCrossBorderOrder } from '../api'
const api = { page: getCrossBorderOrderPage, add: addCrossBorderOrder, update: updateCrossBorderOrder, delete: deleteCrossBorderOrder }
const columns = [{"prop": "orderNo", "label": "订单编号"}, {"prop": "storeName", "label": "店铺"}, {"prop": "customerName", "label": "客户"}, {"prop": "destinationCountry", "label": "目的国"}, {"prop": "currencyCode", "label": "币种"}, {"prop": "orderAmount", "label": "订单金额"}, {"prop": "orderStatus", "label": "订单状态"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "orderNo", "label": "订单编号"}, {"prop": "storeName", "label": "店铺"}, {"prop": "customerName", "label": "客户"}, {"prop": "destinationCountry", "label": "目的国"}, {"prop": "currencyCode", "label": "币种", "type": "select", "options": [{"label": "USD", "value": "USD"}, {"label": "EUR", "value": "EUR"}, {"label": "JPY", "value": "JPY"}, {"label": "GBP", "value": "GBP"}]}, {"prop": "orderAmount", "label": "订单金额", "type": "number"}, {"prop": "orderStatus", "label": "订单状态", "type": "select", "options": [{"label": "CREATED", "value": "CREATED"}, {"label": "DECLARING", "value": "DECLARING"}, {"label": "SHIPPED", "value": "SHIPPED"}, {"label": "COMPLETED", "value": "COMPLETED"}]}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "CREATED", "value": "CREATED"}, {"label": "DECLARING", "value": "DECLARING"}, {"label": "SHIPPED", "value": "SHIPPED"}, {"label": "COMPLETED", "value": "COMPLETED"}]}]
const rowActions = [{"command": "submit", "label": "提交清关", "type": "primary"}, {"command": "ship", "label": "发货", "type": "success"}, {"command": "close", "label": "完成", "type": "success"}]
const defaults = {"currencyCode": "USD", "orderStatus": "CREATED", "status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitCrossBorderOrder(row.id)
  if (command === 'ship') await shipCrossBorderOrder(row.id)
  if (command === 'close') await closeCrossBorderOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
