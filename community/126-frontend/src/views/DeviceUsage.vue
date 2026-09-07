<template>
  <DataPage title="设备用电" description="用电编号、设备、日期、用电量、运行时长和能耗占比管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDeviceUsagePage, addDeviceUsage, updateDeviceUsage, deleteDeviceUsage, warnDeviceUsage, recoverDeviceUsage } from '../api'
const api = { page: getDeviceUsagePage, add: addDeviceUsage, update: updateDeviceUsage, delete: deleteDeviceUsage }
const columns = [{"prop": "usageNo", "label": "用电编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "usageDate", "label": "日期"}, {"prop": "energyKwh", "label": "用电量"}, {"prop": "runtimeHour", "label": "运行小时"}, {"prop": "energyRatio", "label": "能耗占比"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "usageNo", "label": "用电编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "usageDate", "label": "日期"}, {"prop": "energyKwh", "label": "用电量", "type": "number"}, {"prop": "runtimeHour", "label": "运行小时", "type": "number"}, {"prop": "energyRatio", "label": "能耗占比", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "warn", "label": "预警", "type": "warning"}, {"command": "recover", "label": "恢复", "type": "success"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'warn') await warnDeviceUsage(row.id)
  if (command === 'recover') await recoverDeviceUsage(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
