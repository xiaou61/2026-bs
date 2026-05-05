<template>
  <DataPage title="入库订单" description="入库单号、供应商、物料、目标库位、预约时间和入库状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInboundOrderPage, addInboundOrder, updateInboundOrder, deleteInboundOrder, assignInboundOrder, receiveInboundOrder, finishInboundOrder } from '../api'
const api = { page: getInboundOrderPage, add: addInboundOrder, update: updateInboundOrder, delete: deleteInboundOrder }
const columns = [{"prop": "inboundNo", "label": "入库单号"}, {"prop": "supplierName", "label": "供应商"}, {"prop": "itemName", "label": "物料名称"}, {"prop": "targetLocation", "label": "目标库位"}, {"prop": "planTime", "label": "预约时间"}, {"prop": "quantity", "label": "入库数量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "inboundNo", "label": "入库单号"}, {"prop": "supplierName", "label": "供应商"}, {"prop": "itemName", "label": "物料名称"}, {"prop": "targetLocation", "label": "目标库位"}, {"prop": "planTime", "label": "预约时间"}, {"prop": "quantity", "label": "入库数量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "RECEIVED", "value": "RECEIVED"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "assign", "label": "分配库位", "type": "primary"}, {"command": "receive", "label": "收货", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'assign') await assignInboundOrder(row.id)
  if (command === 'receive') await receiveInboundOrder(row.id)
  if (command === 'finish') await finishInboundOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
