<template>
  <DataPage title="保养计划" description="设备保养计划、保养类型、计划时间、负责人、执行结果和计划状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMaintenanceSchedulePage, addMaintenanceSchedule, updateMaintenanceSchedule, deleteMaintenanceSchedule, startMaintenanceSchedule, finishMaintenanceSchedule } from '../api'
const api = { page: getMaintenanceSchedulePage, add: addMaintenanceSchedule, update: updateMaintenanceSchedule, delete: deleteMaintenanceSchedule }
const columns = [{"prop": "scheduleNo", "label": "计划编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "maintenanceType", "label": "保养类型"}, {"prop": "planTime", "label": "计划时间"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "executeResult", "label": "执行结果"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "scheduleNo", "label": "计划编号"}, {"prop": "deviceName", "label": "设备名称"}, {"prop": "maintenanceType", "label": "保养类型", "type": "select", "options": [{"label": "月度保养", "value": "月度保养"}, {"label": "季度保养", "value": "季度保养"}, {"label": "年度保养", "value": "年度保养"}]}, {"prop": "planTime", "label": "计划时间"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "executeResult", "label": "执行结果", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "WAIT_EXECUTE", "value": "WAIT_EXECUTE"}, {"label": "EXECUTING", "value": "EXECUTING"}, {"label": "FINISHED", "value": "FINISHED"}]}]
const rowActions = [{"command": "start", "label": "开始", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "WAIT_EXECUTE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'start') await startMaintenanceSchedule(row.id)
  if (command === 'finish') await finishMaintenanceSchedule(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
