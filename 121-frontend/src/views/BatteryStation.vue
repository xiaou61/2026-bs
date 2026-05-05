<template>
  <DataPage title="电池站点" description="站点编号、站点名称、区域、端口数量、可用端口和管理员管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getBatteryStationPage, addBatteryStation, updateBatteryStation, deleteBatteryStation, enableBatteryStation, warnBatteryStation } from '../api'
const api = { page: getBatteryStationPage, add: addBatteryStation, update: updateBatteryStation, delete: deleteBatteryStation }
const columns = [{"prop": "stationNo", "label": "站点编号"}, {"prop": "stationName", "label": "站点名称"}, {"prop": "zoneName", "label": "巡检区域"}, {"prop": "portCount", "label": "端口数量"}, {"prop": "availablePort", "label": "可用端口"}, {"prop": "managerName", "label": "管理员"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "stationNo", "label": "站点编号"}, {"prop": "stationName", "label": "站点名称"}, {"prop": "zoneName", "label": "巡检区域"}, {"prop": "portCount", "label": "端口数量", "type": "number"}, {"prop": "availablePort", "label": "可用端口", "type": "number"}, {"prop": "managerName", "label": "管理员"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FLYING", "value": "FLYING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "warn", "label": "预警", "type": "warning"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableBatteryStation(row.id)
  if (command === 'warn') await warnBatteryStation(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
