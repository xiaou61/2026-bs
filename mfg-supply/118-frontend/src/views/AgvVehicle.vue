<template>
  <DataPage title="AGV车辆" description="AGV车辆编号、型号、当前区域、电量、任务数量和运行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAgvVehiclePage, addAgvVehicle, updateAgvVehicle, deleteAgvVehicle, onlineAgvVehicle, dispatchAgvVehicle, offlineAgvVehicle } from '../api'
const api = { page: getAgvVehiclePage, add: addAgvVehicle, update: updateAgvVehicle, delete: deleteAgvVehicle }
const columns = [{"prop": "agvNo", "label": "车辆编号"}, {"prop": "agvModel", "label": "车辆型号"}, {"prop": "currentZone", "label": "当前区域"}, {"prop": "batteryLevel", "label": "电量"}, {"prop": "loadWeight", "label": "当前载重"}, {"prop": "taskCount", "label": "今日任务"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "agvNo", "label": "车辆编号"}, {"prop": "agvModel", "label": "车辆型号", "type": "select", "options": [{"label": "潜伏式AGV", "value": "潜伏式AGV"}, {"label": "叉车型AGV", "value": "叉车型AGV"}, {"label": "料箱机器人", "value": "料箱机器人"}]}, {"prop": "currentZone", "label": "当前区域"}, {"prop": "batteryLevel", "label": "电量", "type": "number"}, {"prop": "loadWeight", "label": "当前载重", "type": "number"}, {"prop": "taskCount", "label": "今日任务", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "IDLE", "value": "IDLE"}, {"label": "RUNNING", "value": "RUNNING"}, {"label": "CHARGING", "value": "CHARGING"}, {"label": "FAULT", "value": "FAULT"}, {"label": "OFFLINE", "value": "OFFLINE"}]}]
const rowActions = [{"command": "online", "label": "上线", "type": "success"}, {"command": "dispatch", "label": "调度", "type": "primary"}, {"command": "offline", "label": "下线", "type": "danger"}]
const defaults = {"status": "IDLE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'online') await onlineAgvVehicle(row.id)
  if (command === 'dispatch') await dispatchAgvVehicle(row.id)
  if (command === 'offline') await offlineAgvVehicle(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
