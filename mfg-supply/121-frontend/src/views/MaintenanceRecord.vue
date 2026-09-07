<template>
  <DataPage title="维保记录" description="维保编号、无人机、维保类型、技术员、维保时间和费用管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaintenanceRecordPage, addMaintenanceRecord, updateMaintenanceRecord, deleteMaintenanceRecord, finishMaintenanceRecord, closeMaintenanceRecord } from '../api'
const api = { page: getMaintenanceRecordPage, add: addMaintenanceRecord, update: updateMaintenanceRecord, delete: deleteMaintenanceRecord }
const columns = [{"prop": "maintenanceNo", "label": "维保编号"}, {"prop": "droneName", "label": "无人机"}, {"prop": "maintenanceType", "label": "维保类型"}, {"prop": "technicianName", "label": "技术员"}, {"prop": "maintenanceTime", "label": "维保时间"}, {"prop": "costAmount", "label": "费用"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "maintenanceNo", "label": "维保编号"}, {"prop": "droneName", "label": "无人机"}, {"prop": "maintenanceType", "label": "维保类型"}, {"prop": "technicianName", "label": "技术员"}, {"prop": "maintenanceTime", "label": "维保时间"}, {"prop": "costAmount", "label": "费用", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FLYING", "value": "FLYING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "finish", "label": "完成", "type": "success"}, {"command": "close", "label": "关闭", "type": "info"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'finish') await finishMaintenanceRecord(row.id)
  if (command === 'close') await closeMaintenanceRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
