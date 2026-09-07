<template>
  <DataPage title="运行指标" description="设备传感器指标、温度、振动、电流、采集时间和指标状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSensorMetricPage, addSensorMetric, updateSensorMetric, deleteSensorMetric, warnSensorMetric, recoverSensorMetric } from '../api'
const api = { page: getSensorMetricPage, add: addSensorMetric, update: updateSensorMetric, delete: deleteSensorMetric }
const columns = [{"prop": "metricNo", "label": "指标编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "metricSource", "label": "数据来源"}, {"prop": "temperatureValue", "label": "温度"}, {"prop": "vibrationValue", "label": "振动"}, {"prop": "currentValue", "label": "电流"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "metricNo", "label": "指标编号"}, {"prop": "equipmentName", "label": "设备名称"}, {"prop": "metricSource", "label": "数据来源"}, {"prop": "temperatureValue", "label": "温度", "type": "number"}, {"prop": "vibrationValue", "label": "振动", "type": "number"}, {"prop": "currentValue", "label": "电流", "type": "number"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "ABNORMAL", "value": "ABNORMAL"}]}]
const rowActions = [{"command": "warn", "label": "预警", "type": "warning"}, {"command": "recover", "label": "恢复", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'warn') await warnSensorMetric(row.id)
  if (command === 'recover') await recoverSensorMetric(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
