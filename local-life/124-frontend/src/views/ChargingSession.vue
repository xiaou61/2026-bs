<template>
  <DataPage title="充电记录" description="充电编号、预约单、桩位、开始时间、充电电量和充电金额管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getChargingSessionPage, addChargingSession, updateChargingSession, deleteChargingSession, finishChargingSession, closeChargingSession } from '../api'
const api = { page: getChargingSessionPage, add: addChargingSession, update: updateChargingSession, delete: deleteChargingSession }
const columns = [{"prop": "sessionNo", "label": "充电编号"}, {"prop": "appointmentNo", "label": "预约单号"}, {"prop": "pileNo", "label": "桩位编号"}, {"prop": "startTime", "label": "开始时间"}, {"prop": "energyKwh", "label": "充电电量"}, {"prop": "chargeAmount", "label": "充电金额"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "sessionNo", "label": "充电编号"}, {"prop": "appointmentNo", "label": "预约单号"}, {"prop": "pileNo", "label": "桩位编号"}, {"prop": "startTime", "label": "开始时间"}, {"prop": "energyKwh", "label": "充电电量", "type": "number"}, {"prop": "chargeAmount", "label": "充电金额", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "CHARGING", "value": "CHARGING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "finish", "label": "完成", "type": "success"}, {"command": "close", "label": "关闭", "type": "info"}]
const defaults = {"status": "CHARGING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'finish') await finishChargingSession(row.id)
  if (command === 'close') await closeChargingSession(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
