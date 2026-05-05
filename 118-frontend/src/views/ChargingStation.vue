<template>
  <DataPage title="充电站点" description="充电站编号、所属区域、端口数量、空闲端口和运行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getChargingStationPage, addChargingStation, updateChargingStation, deleteChargingStation, enableChargingStation, maintainChargingStation } from '../api'
const api = { page: getChargingStationPage, add: addChargingStation, update: updateChargingStation, delete: deleteChargingStation }
const columns = [{"prop": "stationNo", "label": "站点编号"}, {"prop": "stationName", "label": "站点名称"}, {"prop": "zoneName", "label": "所属区域"}, {"prop": "portCount", "label": "端口数"}, {"prop": "idlePortCount", "label": "空闲端口"}, {"prop": "chargePower", "label": "充电功率"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "stationNo", "label": "站点编号"}, {"prop": "stationName", "label": "站点名称"}, {"prop": "zoneName", "label": "所属区域"}, {"prop": "portCount", "label": "端口数", "type": "number"}, {"prop": "idlePortCount", "label": "空闲端口", "type": "number"}, {"prop": "chargePower", "label": "充电功率", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "FULL", "value": "FULL"}, {"label": "MAINTAINING", "value": "MAINTAINING"}, {"label": "OFFLINE", "value": "OFFLINE"}]}]
const rowActions = [{"command": "enable", "label": "启用", "type": "success"}, {"command": "maintain", "label": "维护", "type": "warning"}]
const defaults = {"status": "AVAILABLE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'enable') await enableChargingStation(row.id)
  if (command === 'maintain') await maintainChargingStation(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
