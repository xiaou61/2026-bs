<template>
  <DataPage title="出库订单" description="出库单号、客户、物料、拣选库位、优先级和出库状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getOutboundOrderPage, addOutboundOrder, updateOutboundOrder, deleteOutboundOrder, allocateOutboundOrder, pickOutboundOrder, shipOutboundOrder } from '../api'
const api = { page: getOutboundOrderPage, add: addOutboundOrder, update: updateOutboundOrder, delete: deleteOutboundOrder }
const columns = [{"prop": "outboundNo", "label": "出库单号"}, {"prop": "customerName", "label": "客户名称"}, {"prop": "itemName", "label": "物料名称"}, {"prop": "pickLocation", "label": "拣选库位"}, {"prop": "priorityLevel", "label": "优先级"}, {"prop": "quantity", "label": "出库数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "outboundNo", "label": "出库单号"}, {"prop": "customerName", "label": "客户名称"}, {"prop": "itemName", "label": "物料名称"}, {"prop": "pickLocation", "label": "拣选库位"}, {"prop": "priorityLevel", "label": "优先级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "quantity", "label": "出库数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "CREATED", "value": "CREATED"}, {"label": "ALLOCATED", "value": "ALLOCATED"}, {"label": "PICKING", "value": "PICKING"}, {"label": "SHIPPED", "value": "SHIPPED"}]}]
const rowActions = [{"command": "allocate", "label": "分配任务", "type": "primary"}, {"command": "pick", "label": "拣选", "type": "success"}, {"command": "ship", "label": "出库", "type": "success"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'allocate') await allocateOutboundOrder(row.id)
  if (command === 'pick') await pickOutboundOrder(row.id)
  if (command === 'ship') await shipOutboundOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
