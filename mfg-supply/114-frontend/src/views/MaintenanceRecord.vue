<template>
  <DataPage title="设备维护" description="温控设备巡检、维修、校准和维护结果管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaintenanceRecordPage, addMaintenanceRecord, updateMaintenanceRecord, deleteMaintenanceRecord, submitMaintenanceRecord, finishMaintenanceRecord } from '../api'
const api = { page: getMaintenanceRecordPage, add: addMaintenanceRecord, update: updateMaintenanceRecord, delete: deleteMaintenanceRecord }
const columns = [{"prop": "maintenanceNo", "label": "维护编号"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "maintainerName", "label": "维护人"}, {"prop": "maintenanceType", "label": "维护类型"}, {"prop": "resultStatus", "label": "结果"}, {"prop": "detailText", "label": "详情"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "maintenanceNo", "label": "维护编号"}, {"prop": "deviceNo", "label": "设备编号"}, {"prop": "maintainerName", "label": "维护人"}, {"prop": "maintenanceType", "label": "维护类型", "type": "select", "options": [{"label": "CHECK", "value": "CHECK"}, {"label": "REPAIR", "value": "REPAIR"}, {"label": "CALIBRATE", "value": "CALIBRATE"}]}, {"prop": "resultStatus", "label": "结果", "type": "select", "options": [{"label": "PASS", "value": "PASS"}, {"label": "REPAIR", "value": "REPAIR"}, {"label": "FAILED", "value": "FAILED"}]}, {"prop": "detailText", "label": "详情", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"resultStatus": "PASS", "status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitMaintenanceRecord(row.id)
  if (command === 'finish') await finishMaintenanceRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
