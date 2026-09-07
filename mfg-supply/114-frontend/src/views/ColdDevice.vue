<template>
  <DataPage title="温控设备" description="车载温控设备、传感器编号、绑定车辆和在线状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getColdDevicePage, addColdDevice, updateColdDevice, deleteColdDevice, onlineColdDevice, offlineColdDevice, repairColdDevice } from '../api'
const api = { page: getColdDevicePage, add: addColdDevice, update: updateColdDevice, delete: deleteColdDevice }
const columns = [{"prop": "deviceName", "label": "设备名称"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "vehicleNo", "label": "车辆编号"}, {"prop": "ownerName", "label": "所属企业"}, {"prop": "onlineStatus", "label": "在线状态"}, {"prop": "lastHeartbeat", "label": "最近心跳"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "deviceName", "label": "设备名称"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "vehicleNo", "label": "车辆编号"}, {"prop": "ownerName", "label": "所属企业"}, {"prop": "onlineStatus", "label": "在线状态", "type": "select", "options": [{"label": "ONLINE", "value": "ONLINE"}, {"label": "OFFLINE", "value": "OFFLINE"}, {"label": "FAULT", "value": "FAULT"}]}, {"prop": "lastHeartbeat", "label": "最近心跳"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "OFFLINE", "value": "OFFLINE"}, {"label": "REPAIRING", "value": "REPAIRING"}]}]
const rowActions = [{"command": "online", "label": "上线", "type": "primary"}, {"command": "offline", "label": "离线", "type": "primary"}, {"command": "repair", "label": "检修", "type": "primary"}]
const defaults = {"onlineStatus": "ONLINE", "status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'online') await onlineColdDevice(row.id)
  if (command === 'offline') await offlineColdDevice(row.id)
  if (command === 'repair') await repairColdDevice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
