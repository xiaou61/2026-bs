<template>
  <DataPage title="中期检查" description="检查编号、课题编号、检查节点、检查教师、检查时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDeviceMonitorPage, addDeviceMonitor, updateDeviceMonitor, deleteDeviceMonitor, activateDeviceMonitor, finishDeviceMonitor } from '../api'
const api = { page: getDeviceMonitorPage, add: addDeviceMonitor, update: updateDeviceMonitor, delete: deleteDeviceMonitor }
const columns = [{"prop": "patentNo", "label": "检查编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "patentName", "label": "检查节点"}, {"prop": "applicantName", "label": "检查教师"}, {"prop": "grantTime", "label": "检查时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "patentNo", "label": "检查编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "patentName", "label": "检查节点"}, {"prop": "applicantName", "label": "检查教师"}, {"prop": "grantTime", "label": "检查时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "PUBLISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateDeviceMonitor(row.id)
  if (command === 'finish') await finishDeviceMonitor(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>



