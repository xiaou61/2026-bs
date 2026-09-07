<template>
  <DataPage title="温控记录" description="运输过程温度、湿度、设备编号和采集时间管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTemperatureRecordPage, addTemperatureRecord, updateTemperatureRecord, deleteTemperatureRecord, normalTemperatureRecord, warnTemperatureRecord, alarmTemperatureRecord } from '../api'
const api = { page: getTemperatureRecordPage, add: addTemperatureRecord, update: updateTemperatureRecord, delete: deleteTemperatureRecord }
const columns = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "cargoName", "label": "货品"}, {"prop": "temperatureValue", "label": "温度"}, {"prop": "humidityValue", "label": "湿度"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "orderNo", "label": "订单编号"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "cargoName", "label": "货品"}, {"prop": "temperatureValue", "label": "温度", "type": "number"}, {"prop": "humidityValue", "label": "湿度", "type": "number"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "ALARM", "value": "ALARM"}]}]
const rowActions = [{"command": "normal", "label": "正常", "type": "primary"}, {"command": "warn", "label": "预警", "type": "primary"}, {"command": "alarm", "label": "告警", "type": "primary"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'normal') await normalTemperatureRecord(row.id)
  if (command === 'warn') await warnTemperatureRecord(row.id)
  if (command === 'alarm') await alarmTemperatureRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
