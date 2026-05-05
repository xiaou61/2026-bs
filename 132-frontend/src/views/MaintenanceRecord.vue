<template>
  <DataPage title="维护记录" description="维护编号、器械、维护类型、维护时间、维护人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaintenanceRecordPage, addMaintenanceRecord, updateMaintenanceRecord, deleteMaintenanceRecord, submitMaintenanceRecord, approveMaintenanceRecord } from '../api'
const api = { page: getMaintenanceRecordPage, add: addMaintenanceRecord, update: updateMaintenanceRecord, delete: deleteMaintenanceRecord }
const columns = [{"prop": "maintenanceNo", "label": "维护编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "maintenanceType", "label": "维护类型"}, {"prop": "maintenanceTime", "label": "维护时间"}, {"prop": "maintainerName", "label": "维护人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "maintenanceNo", "label": "维护编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "maintenanceType", "label": "维护类型"}, {"prop": "maintenanceTime", "label": "维护时间"}, {"prop": "maintainerName", "label": "维护人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitMaintenanceRecord(row.id)
  if (command === 'approve') await approveMaintenanceRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
