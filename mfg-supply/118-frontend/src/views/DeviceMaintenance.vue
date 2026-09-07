<template>
  <DataPage title="设备维保" description="AGV或充电站维保计划、故障类型、处理人、计划时间和维保状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDeviceMaintenancePage, addDeviceMaintenance, updateDeviceMaintenance, deleteDeviceMaintenance, startDeviceMaintenance, finishDeviceMaintenance } from '../api'
const api = { page: getDeviceMaintenancePage, add: addDeviceMaintenance, update: updateDeviceMaintenance, delete: deleteDeviceMaintenance }
const columns = [{"prop": "maintenanceNo", "label": "维保编号"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "deviceType", "label": "设备类型"}, {"prop": "faultType", "label": "故障类型"}, {"prop": "planTime", "label": "计划时间"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "maintenanceNo", "label": "维保编号"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "deviceType", "label": "设备类型", "type": "select", "options": [{"label": "AGV车辆", "value": "AGV车辆"}, {"label": "充电站", "value": "充电站"}, {"label": "传感器", "value": "传感器"}]}, {"prop": "faultType", "label": "故障类型", "type": "select", "options": [{"label": "电池衰减", "value": "电池衰减"}, {"label": "导航偏移", "value": "导航偏移"}, {"label": "充电异常", "value": "充电异常"}, {"label": "通信中断", "value": "通信中断"}]}, {"prop": "planTime", "label": "计划时间"}, {"prop": "handlerName", "label": "处理人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_HANDLE", "value": "WAIT_HANDLE"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "start", "label": "开始", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "WAIT_HANDLE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'start') await startDeviceMaintenance(row.id)
  if (command === 'finish') await finishDeviceMaintenance(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
