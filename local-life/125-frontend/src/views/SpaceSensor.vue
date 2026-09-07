<template>
  <DataPage title="车位传感器" description="传感器编号、车位、设备类型、电量、信号强度和在线状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSpaceSensorPage, addSpaceSensor, updateSpaceSensor, deleteSpaceSensor, onlineSpaceSensor, warnSpaceSensor } from '../api'
const api = { page: getSpaceSensorPage, add: addSpaceSensor, update: updateSpaceSensor, delete: deleteSpaceSensor }
const columns = [{"prop": "sensorNo", "label": "传感器编号"}, {"prop": "spaceNo", "label": "车位编号"}, {"prop": "deviceType", "label": "设备类型"}, {"prop": "batteryLevel", "label": "电量"}, {"prop": "signalStrength", "label": "信号强度"}, {"prop": "lastHeartbeat", "label": "最后心跳"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "sensorNo", "label": "传感器编号"}, {"prop": "spaceNo", "label": "车位编号"}, {"prop": "deviceType", "label": "设备类型"}, {"prop": "batteryLevel", "label": "电量", "type": "number"}, {"prop": "signalStrength", "label": "信号强度", "type": "number"}, {"prop": "lastHeartbeat", "label": "最后心跳"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "ENTERED", "value": "ENTERED"}, {"label": "LEFT", "value": "LEFT"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "online", "label": "在线", "type": "success"}, {"command": "warn", "label": "预警", "type": "warning"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'online') await onlineSpaceSensor(row.id)
  if (command === 'warn') await warnSpaceSensor(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
