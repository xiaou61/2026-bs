<template>
  <DataPage title="调度任务" description="任务编号、预警编号、任务类型、责任单位、截止时间和执行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDispatchTaskPage, addDispatchTask, updateDispatchTask, deleteDispatchTask, startDispatchTask, finishDispatchTask } from '../api'
const api = { page: getDispatchTaskPage, add: addDispatchTask, update: updateDispatchTask, delete: deleteDispatchTask }
const columns = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "warningNo", "label": "预警编号"}, {"prop": "taskType", "label": "任务类型"}, {"prop": "responsibleUnit", "label": "责任单位"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "dispatcherName", "label": "调度员"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "warningNo", "label": "预警编号"}, {"prop": "taskType", "label": "任务类型"}, {"prop": "responsibleUnit", "label": "责任单位"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "dispatcherName", "label": "调度员"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "start", "label": "开始", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ASSIGNED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'start') await startDispatchTask(row.id)
  if (command === 'finish') await finishDispatchTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
