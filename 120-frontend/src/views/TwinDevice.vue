<template>
  <DataPage title="孪生设备" description="设备编号、设备名称、所属楼宇、设备类型、模型编号和运行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTwinDevicePage, addTwinDevice, updateTwinDevice, deleteTwinDevice, onlineTwinDevice, offlineTwinDevice } from '../api'
const api = { page: getTwinDevicePage, add: addTwinDevice, update: updateTwinDevice, delete: deleteTwinDevice }
const columns = [{"prop": "deviceNo", "label": "设备编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "buildingName", "label": "所属楼宇"}, {"prop": "deviceType", "label": "设备类型"}, {"prop": "modelNo", "label": "模型编号"}, {"prop": "healthScore", "label": "健康分"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "deviceNo", "label": "设备编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "buildingName", "label": "所属楼宇"}, {"prop": "deviceType", "label": "设备类型", "type": "select", "options": [{"label": "暖通空调", "value": "暖通空调"}, {"label": "电梯", "value": "电梯"}, {"label": "配电柜", "value": "配电柜"}, {"label": "消防设备", "value": "消防设备"}]}, {"prop": "modelNo", "label": "模型编号"}, {"prop": "healthScore", "label": "健康分", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ONLINE", "value": "ONLINE"}, {"label": "OFFLINE", "value": "OFFLINE"}, {"label": "ALARM", "value": "ALARM"}]}]
const rowActions = [{"command": "online", "label": "上线", "type": "success"}, {"command": "offline", "label": "离线", "type": "danger"}]
const defaults = {"status": "ONLINE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'online') await onlineTwinDevice(row.id)
  if (command === 'offline') await offlineTwinDevice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
