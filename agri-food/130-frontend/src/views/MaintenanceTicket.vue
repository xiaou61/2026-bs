<template>
  <DataPage title="维护工单" description="工单编号、设备、问题类型、负责人、截止时间和处理状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaintenanceTicketPage, addMaintenanceTicket, updateMaintenanceTicket, deleteMaintenanceTicket, processMaintenanceTicket, finishMaintenanceTicket } from '../api'
const api = { page: getMaintenanceTicketPage, add: addMaintenanceTicket, update: updateMaintenanceTicket, delete: deleteMaintenanceTicket }
const columns = [{"prop": "ticketNo", "label": "工单编号"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "issueType", "label": "问题类型"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "ticketNo", "label": "工单编号"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "issueType", "label": "问题类型"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processMaintenanceTicket(row.id)
  if (command === 'finish') await finishMaintenanceTicket(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
