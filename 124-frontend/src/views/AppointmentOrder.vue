<template>
  <DataPage title="预约订单" description="预约单号、车主、站点、桩位、预约时间和预计时长管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAppointmentOrderPage, addAppointmentOrder, updateAppointmentOrder, deleteAppointmentOrder, reserveAppointmentOrder, finishAppointmentOrder } from '../api'
const api = { page: getAppointmentOrderPage, add: addAppointmentOrder, update: updateAppointmentOrder, delete: deleteAppointmentOrder }
const columns = [{"prop": "appointmentNo", "label": "预约单号"}, {"prop": "ownerName", "label": "车主"}, {"prop": "stationName", "label": "站点"}, {"prop": "pileNo", "label": "桩位编号"}, {"prop": "appointmentTime", "label": "预约时间"}, {"prop": "durationMinute", "label": "预计分钟"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "appointmentNo", "label": "预约单号"}, {"prop": "ownerName", "label": "车主"}, {"prop": "stationName", "label": "站点"}, {"prop": "pileNo", "label": "桩位编号"}, {"prop": "appointmentTime", "label": "预约时间"}, {"prop": "durationMinute", "label": "预计分钟", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "AVAILABLE", "value": "AVAILABLE"}, {"label": "OCCUPIED", "value": "OCCUPIED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "RESERVED", "value": "RESERVED"}, {"label": "CHARGING", "value": "CHARGING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PAID", "value": "PAID"}, {"label": "REFUNDED", "value": "REFUNDED"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "reserve", "label": "预约", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'reserve') await reserveAppointmentOrder(row.id)
  if (command === 'finish') await finishAppointmentOrder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
