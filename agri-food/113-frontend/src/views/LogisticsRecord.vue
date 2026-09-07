<template>
  <DataPage title="物流记录" description="批次流转、温湿度、承运方和签收状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getLogisticsRecordPage, addLogisticsRecord, updateLogisticsRecord, deleteLogisticsRecord, departLogisticsRecord, arriveLogisticsRecord, signLogisticsRecord } from '../api'
const api = { page: getLogisticsRecordPage, add: addLogisticsRecord, update: updateLogisticsRecord, delete: deleteLogisticsRecord }
const columns = [{"prop": "logisticsNo", "label": "物流编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "fromNode", "label": "出发节点"}, {"prop": "toNode", "label": "到达节点"}, {"prop": "carrierName", "label": "承运方"}, {"prop": "temperatureText", "label": "温控记录"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "logisticsNo", "label": "物流编号"}, {"prop": "batchNo", "label": "批次编号"}, {"prop": "fromNode", "label": "出发节点"}, {"prop": "toNode", "label": "到达节点"}, {"prop": "carrierName", "label": "承运方"}, {"prop": "temperatureText", "label": "温控记录"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_DEPART", "value": "WAIT_DEPART"}, {"label": "IN_TRANSIT", "value": "IN_TRANSIT"}, {"label": "ARRIVED", "value": "ARRIVED"}, {"label": "SIGNED", "value": "SIGNED"}]}]
const rowActions = [{"command": "depart", "label": "发车", "type": "primary"}, {"command": "arrive", "label": "到达", "type": "primary"}, {"command": "sign", "label": "签收", "type": "primary"}]
const defaults = {"status": "IN_TRANSIT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'depart') await departLogisticsRecord(row.id)
  if (command === 'arrive') await arriveLogisticsRecord(row.id)
  if (command === 'sign') await signLogisticsRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
