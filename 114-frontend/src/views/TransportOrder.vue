<template>
  <DataPage title="运输订单" description="运输任务、起止节点、承运企业和订单状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTransportOrderPage, addTransportOrder, updateTransportOrder, deleteTransportOrder, dispatchTransportOrder, startTransportOrder, finishTransportOrder } from '../api'
const api = { page: getTransportOrderPage, add: addTransportOrder, update: updateTransportOrder, delete: deleteTransportOrder }
const columns = [{"prop": "orderNo", "label": "订单编号"}, {"prop": "cargoName", "label": "货品"}, {"prop": "fromNode", "label": "起点"}, {"prop": "toNode", "label": "终点"}, {"prop": "carrierName", "label": "承运企业"}, {"prop": "vehicleNo", "label": "车辆"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "orderNo", "label": "订单编号"}, {"prop": "cargoName", "label": "货品"}, {"prop": "fromNode", "label": "起点"}, {"prop": "toNode", "label": "终点"}, {"prop": "carrierName", "label": "承运企业"}, {"prop": "vehicleNo", "label": "车辆"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "DISPATCHED", "value": "DISPATCHED"}, {"label": "IN_TRANSIT", "value": "IN_TRANSIT"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "dispatch", "label": "调度", "type": "primary"}, {"command": "start", "label": "启运", "type": "primary"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'dispatch') await dispatchTransportOrder(row.id)
  if (command === 'start') await startTransportOrder(row.id)
  if (command === 'finish') await finishTransportOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
