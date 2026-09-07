<template>
  <DataPage title="灌溉任务" description="灌溉任务、温室、任务时间、水量和执行人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getIrrigationTaskPage, addIrrigationTask, updateIrrigationTask, deleteIrrigationTask, processIrrigationTask, finishIrrigationTask } from '../api'
const api = { page: getIrrigationTaskPage, add: addIrrigationTask, update: updateIrrigationTask, delete: deleteIrrigationTask }
const columns = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "taskTime", "label": "任务时间"}, {"prop": "waterAmount", "label": "灌溉水量"}, {"prop": "executorName", "label": "执行人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "greenhouseNo", "label": "温室编号"}, {"prop": "taskTime", "label": "任务时间"}, {"prop": "waterAmount", "label": "灌溉水量", "type": "number"}, {"prop": "executorName", "label": "执行人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processIrrigationTask(row.id)
  if (command === 'finish') await finishIrrigationTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
