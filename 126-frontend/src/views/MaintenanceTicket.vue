<template>
  <DataPage title="维修工单" description="工单编号、家庭、设备、问题类型、维修员和预约时间管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaintenanceTicketPage, addMaintenanceTicket, updateMaintenanceTicket, deleteMaintenanceTicket, assignMaintenanceTicket, finishMaintenanceTicket } from '../api'
const api = { page: getMaintenanceTicketPage, add: addMaintenanceTicket, update: updateMaintenanceTicket, delete: deleteMaintenanceTicket }
const columns = [{"prop": "ticketNo", "label": "工单编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "issueType", "label": "问题类型"}, {"prop": "maintainerName", "label": "维修员"}, {"prop": "appointmentTime", "label": "预约时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "ticketNo", "label": "工单编号"}, {"prop": "homeNo", "label": "家庭编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "issueType", "label": "问题类型"}, {"prop": "maintainerName", "label": "维修员"}, {"prop": "appointmentTime", "label": "预约时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "CREATED", "value": "CREATED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "PAID", "value": "PAID"}, {"label": "OVER_BUDGET", "value": "OVER_BUDGET"}]}]
const rowActions = [{"command": "assign", "label": "派发", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'assign') await assignMaintenanceTicket(row.id)
  if (command === 'finish') await finishMaintenanceTicket(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
