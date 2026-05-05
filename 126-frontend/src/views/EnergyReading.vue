<template>
  <DataPage title="用电读数" description="读数编号、电表、采集时间、总电量、峰电量和谷电量管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEnergyReadingPage, addEnergyReading, updateEnergyReading, deleteEnergyReading, warnEnergyReading, recoverEnergyReading } from '../api'
const api = { page: getEnergyReadingPage, add: addEnergyReading, update: updateEnergyReading, delete: deleteEnergyReading }
const columns = [{"prop": "readingNo", "label": "读数编号"}, {"prop": "meterNo", "label": "电表编号"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "totalKwh", "label": "总电量"}, {"prop": "peakKwh", "label": "峰电量"}, {"prop": "valleyKwh", "label": "谷电量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "readingNo", "label": "读数编号"}, {"prop": "meterNo", "label": "电表编号"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "totalKwh", "label": "总电量", "type": "number"}, {"prop": "peakKwh", "label": "峰电量", "type": "number"}, {"prop": "valleyKwh", "label": "谷电量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "warn", "label": "预警", "type": "warning"}, {"command": "recover", "label": "恢复", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'warn') await warnEnergyReading(row.id)
  if (command === 'recover') await recoverEnergyReading(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
