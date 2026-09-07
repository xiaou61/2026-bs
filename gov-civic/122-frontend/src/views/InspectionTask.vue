<template>
  <DataPage title="巡检任务" description="任务单号、计划、巡检员、检查区域、优先级和截止时间管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInspectionTaskPage, addInspectionTask, updateInspectionTask, deleteInspectionTask, startInspectionTask, finishInspectionTask } from '../api'
const api = { page: getInspectionTaskPage, add: addInspectionTask, update: updateInspectionTask, delete: deleteInspectionTask }
const columns = [{"prop": "taskNo", "label": "任务单号"}, {"prop": "planNo", "label": "计划编号"}, {"prop": "inspectorName", "label": "巡检员"}, {"prop": "checkArea", "label": "检查区域"}, {"prop": "priorityLevel", "label": "优先级"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务单号"}, {"prop": "planNo", "label": "计划编号"}, {"prop": "inspectorName", "label": "巡检员"}, {"prop": "checkArea", "label": "检查区域"}, {"prop": "priorityLevel", "label": "优先级", "type": "select", "options": [{"label": "高", "value": "高"}, {"label": "中", "value": "中"}, {"label": "低", "value": "低"}]}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "CREATED", "value": "CREATED"}, {"label": "ASSIGNED", "value": "ASSIGNED"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "CLOSED", "value": "CLOSED"}, {"label": "PASS", "value": "PASS"}, {"label": "FAIL", "value": "FAIL"}, {"label": "WARNING", "value": "WARNING"}]}]
const rowActions = [{"command": "start", "label": "开始", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "ASSIGNED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'start') await startInspectionTask(row.id)
  if (command === 'finish') await finishInspectionTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
