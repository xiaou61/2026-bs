<template>
  <DataPage title="传感数据" description="设备传感数据、温度、能耗、压力、采集时间和数据状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSensorDataPage, addSensorData, updateSensorData, deleteSensorData, warnSensorData, recoverSensorData } from '../api'
const api = { page: getSensorDataPage, add: addSensorData, update: updateSensorData, delete: deleteSensorData }
const columns = [{"prop": "dataNo", "label": "数据编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "sensorType", "label": "传感类型"}, {"prop": "temperatureValue", "label": "温度"}, {"prop": "energyValue", "label": "能耗"}, {"prop": "pressureValue", "label": "压力"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "dataNo", "label": "数据编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "sensorType", "label": "传感类型", "type": "select", "options": [{"label": "温湿度", "value": "温湿度"}, {"label": "电表", "value": "电表"}, {"label": "水压", "value": "水压"}, {"label": "振动", "value": "振动"}]}, {"prop": "temperatureValue", "label": "温度", "type": "number"}, {"prop": "energyValue", "label": "能耗", "type": "number"}, {"prop": "pressureValue", "label": "压力", "type": "number"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "ABNORMAL", "value": "ABNORMAL"}]}]
const rowActions = [{"command": "warn", "label": "预警", "type": "warning"}, {"command": "recover", "label": "恢复", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'warn') await warnSensorData(row.id)
  if (command === 'recover') await recoverSensorData(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
