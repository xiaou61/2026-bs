<template>
  <DataPage title="无人机设备" description="设备编号、名称、型号、电量、所属队伍和运行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDroneDevicePage, addDroneDevice, updateDroneDevice, deleteDroneDevice, onlineDroneDevice, offlineDroneDevice } from '../api'
const api = { page: getDroneDevicePage, add: addDroneDevice, update: updateDroneDevice, delete: deleteDroneDevice }
const columns = [{"prop": "droneNo", "label": "设备编号"}, {"prop": "droneName", "label": "设备名称"}, {"prop": "modelName", "label": "机型"}, {"prop": "batteryLevel", "label": "电量"}, {"prop": "ownerTeam", "label": "所属队伍"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "droneNo", "label": "设备编号"}, {"prop": "droneName", "label": "设备名称"}, {"prop": "modelName", "label": "机型"}, {"prop": "batteryLevel", "label": "电量", "type": "number"}, {"prop": "ownerTeam", "label": "所属队伍"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FLYING", "value": "FLYING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "online", "label": "上线", "type": "success"}, {"command": "offline", "label": "下线", "type": "danger"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'online') await onlineDroneDevice(row.id)
  if (command === 'offline') await offlineDroneDevice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
