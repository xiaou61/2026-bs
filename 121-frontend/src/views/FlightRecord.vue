<template>
  <DataPage title="飞行记录" description="飞行记录编号、任务单号、无人机、起飞时间、飞行时长和巡检结果管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFlightRecordPage, addFlightRecord, updateFlightRecord, deleteFlightRecord, finishFlightRecord, warnFlightRecord } from '../api'
const api = { page: getFlightRecordPage, add: addFlightRecord, update: updateFlightRecord, delete: deleteFlightRecord }
const columns = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "taskNo", "label": "任务单号"}, {"prop": "droneName", "label": "无人机"}, {"prop": "startTime", "label": "起飞时间"}, {"prop": "flightDuration", "label": "飞行分钟"}, {"prop": "resultType", "label": "巡检结果"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "taskNo", "label": "任务单号"}, {"prop": "droneName", "label": "无人机"}, {"prop": "startTime", "label": "起飞时间"}, {"prop": "flightDuration", "label": "飞行分钟", "type": "number"}, {"prop": "resultType", "label": "巡检结果"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FLYING", "value": "FLYING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "finish", "label": "完成", "type": "success"}, {"command": "warn", "label": "预警", "type": "warning"}]
const defaults = {"status": "FLYING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'finish') await finishFlightRecord(row.id)
  if (command === 'warn') await warnFlightRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
