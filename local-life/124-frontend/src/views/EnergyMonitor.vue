<template>
  <DataPage title="能耗监测" description="监测编号、站点、采集时间、用电量、负载率和异常类型管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEnergyMonitorPage, addEnergyMonitor, updateEnergyMonitor, deleteEnergyMonitor, warnEnergyMonitor, recoverEnergyMonitor } from '../api'
const api = { page: getEnergyMonitorPage, add: addEnergyMonitor, update: updateEnergyMonitor, delete: deleteEnergyMonitor }
const columns = [{"prop": "monitorNo", "label": "监测编号"}, {"prop": "stationName", "label": "站点"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "powerKwh", "label": "用电量"}, {"prop": "loadRate", "label": "负载率"}, {"prop": "abnormalType", "label": "异常类型"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "monitorNo", "label": "监测编号"}, {"prop": "stationName", "label": "站点"}, {"prop": "collectTime", "label": "采集时间"}, {"prop": "powerKwh", "label": "用电量", "type": "number"}, {"prop": "loadRate", "label": "负载率", "type": "number"}, {"prop": "abnormalType", "label": "异常类型"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "CHARGING", "value": "CHARGING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "warn", "label": "预警", "type": "warning"}, {"command": "recover", "label": "恢复", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'warn') await warnEnergyMonitor(row.id)
  if (command === 'recover') await recoverEnergyMonitor(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
