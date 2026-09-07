<template>
  <DataPage title="雨量站点" description="站点编号、名称、区域、设备类型、采样频率和负责人管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRainGaugeStationPage, addRainGaugeStation, updateRainGaugeStation, deleteRainGaugeStation, enableRainGaugeStation, disableRainGaugeStation } from '../api'
const api = { page: getRainGaugeStationPage, add: addRainGaugeStation, update: updateRainGaugeStation, delete: deleteRainGaugeStation }
const columns = [{"prop": "stationNo", "label": "站点编号"}, {"prop": "stationName", "label": "站点名称"}, {"prop": "districtName", "label": "行政区"}, {"prop": "deviceType", "label": "设备类型"}, {"prop": "sampleMinute", "label": "采样分钟"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "stationNo", "label": "站点编号"}, {"prop": "stationName", "label": "站点名称"}, {"prop": "districtName", "label": "行政区"}, {"prop": "deviceType", "label": "设备类型"}, {"prop": "sampleMinute", "label": "采样分钟", "type": "number"}, {"prop": "managerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "disable", "label": "停用", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableRainGaugeStation(row.id)
  if (command === 'disable') await disableRainGaugeStation(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
