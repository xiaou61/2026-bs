<template>
  <DataPage title="巡检任务" description="巡检编号、器械、巡检日期、巡检人和任务状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getInspectionTaskPage, addInspectionTask, updateInspectionTask, deleteInspectionTask, processInspectionTask, finishInspectionTask } from '../api'
const api = { page: getInspectionTaskPage, add: addInspectionTask, update: updateInspectionTask, delete: deleteInspectionTask }
const columns = [{"prop": "inspectionNo", "label": "巡检编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "inspectionDate", "label": "巡检日期"}, {"prop": "inspectorName", "label": "巡检人"}, {"prop": "resultText", "label": "巡检结果"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "inspectionNo", "label": "巡检编号"}, {"prop": "deviceNo", "label": "器械编号"}, {"prop": "inspectionDate", "label": "巡检日期"}, {"prop": "inspectorName", "label": "巡检人"}, {"prop": "resultText", "label": "巡检结果"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processInspectionTask(row.id)
  if (command === 'finish') await finishInspectionTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
