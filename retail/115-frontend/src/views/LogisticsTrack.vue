<template>
  <DataPage title="物流跟踪" description="国际物流单号、承运商、当前节点、位置、轨迹时间和签收状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getLogisticsTrackPage, addLogisticsTrack, updateLogisticsTrack, deleteLogisticsTrack, departLogisticsTrack, arriveLogisticsTrack, signLogisticsTrack } from '../api'
const api = { page: getLogisticsTrackPage, add: addLogisticsTrack, update: updateLogisticsTrack, delete: deleteLogisticsTrack }
const columns = [{"prop": "trackingNo", "label": "物流单号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "carrierName", "label": "承运商"}, {"prop": "currentNode", "label": "当前节点"}, {"prop": "locationText", "label": "位置"}, {"prop": "trackTime", "label": "轨迹时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "trackingNo", "label": "物流单号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "carrierName", "label": "承运商"}, {"prop": "currentNode", "label": "当前节点"}, {"prop": "locationText", "label": "位置"}, {"prop": "trackTime", "label": "轨迹时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "CREATED", "value": "CREATED"}, {"label": "IN_TRANSIT", "value": "IN_TRANSIT"}, {"label": "ARRIVED", "value": "ARRIVED"}, {"label": "SIGNED", "value": "SIGNED"}]}]
const rowActions = [{"command": "depart", "label": "出发", "type": "primary"}, {"command": "arrive", "label": "到港", "type": "success"}, {"command": "sign", "label": "签收", "type": "success"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'depart') await departLogisticsTrack(row.id)
  if (command === 'arrive') await arriveLogisticsTrack(row.id)
  if (command === 'sign') await signLogisticsTrack(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
