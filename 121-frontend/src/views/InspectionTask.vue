<template>
  <DataPage title="巡检任务" description="任务单号、航线、飞手、计划时间、优先级和执行状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInspectionTaskPage, addInspectionTask, updateInspectionTask, deleteInspectionTask, assignInspectionTask, finishInspectionTask } from '../api'
const api = { page: getInspectionTaskPage, add: addInspectionTask, update: updateInspectionTask, delete: deleteInspectionTask }
const columns = [{"prop": "taskNo", "label": "任务单号"}, {"prop": "routeName", "label": "航线名称"}, {"prop": "pilotName", "label": "飞手姓名"}, {"prop": "planTime", "label": "计划时间"}, {"prop": "priorityLevel", "label": "优先级"}, {"prop": "photoRequired", "label": "需拍照片"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务单号"}, {"prop": "routeName", "label": "航线名称"}, {"prop": "pilotName", "label": "飞手姓名"}, {"prop": "planTime", "label": "计划时间"}, {"prop": "priorityLevel", "label": "优先级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "photoRequired", "label": "需拍照片", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "FLYING", "value": "FLYING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "assign", "label": "派发", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "CREATED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'assign') await assignInspectionTask(row.id)
  if (command === 'finish') await finishInspectionTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
