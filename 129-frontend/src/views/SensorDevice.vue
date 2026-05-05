<template>
  <DataPage title="传感设备" description="传感器编号、池塘、设备类型、安装位置和电量维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSensorDevicePage, addSensorDevice, updateSensorDevice, deleteSensorDevice, activateSensorDevice, finishSensorDevice } from '../api'
const api = { page: getSensorDevicePage, add: addSensorDevice, update: updateSensorDevice, delete: deleteSensorDevice }
const columns = [{"prop": "sensorNo", "label": "传感器编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "sensorType", "label": "设备类型"}, {"prop": "installPosition", "label": "安装位置"}, {"prop": "batteryLevel", "label": "电量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "sensorNo", "label": "传感器编号"}, {"prop": "pondNo", "label": "池塘编号"}, {"prop": "sensorType", "label": "设备类型"}, {"prop": "installPosition", "label": "安装位置"}, {"prop": "batteryLevel", "label": "电量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateSensorDevice(row.id)
  if (command === 'finish') await finishSensorDevice(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
