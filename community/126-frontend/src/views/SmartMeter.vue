<template>
  <DataPage title="智能电表" description="电表编号、家庭、安装位置、通信方式、倍率和在线状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getSmartMeterPage, addSmartMeter, updateSmartMeter, deleteSmartMeter, onlineSmartMeter, warnSmartMeter } from '../api'
const api = { page: getSmartMeterPage, add: addSmartMeter, update: updateSmartMeter, delete: deleteSmartMeter }
const columns = [{"prop": "meterNo", "label": "电表编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "installLocation", "label": "安装位置"}, {"prop": "communicationType", "label": "通信方式"}, {"prop": "ratioValue", "label": "倍率"}, {"prop": "lastHeartbeat", "label": "最后心跳"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "meterNo", "label": "电表编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "installLocation", "label": "安装位置"}, {"prop": "communicationType", "label": "通信方式"}, {"prop": "ratioValue", "label": "倍率", "type": "number"}, {"prop": "lastHeartbeat", "label": "最后心跳"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "online", "label": "在线", "type": "success"}, {"command": "warn", "label": "预警", "type": "warning"}]
const defaults = {"status": "NORMAL"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'online') await onlineSmartMeter(row.id)
  if (command === 'warn') await warnSmartMeter(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
