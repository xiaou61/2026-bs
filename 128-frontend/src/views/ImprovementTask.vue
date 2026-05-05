<template>
  <DataPage title="改进任务" description="改进任务、责任部门、整改事项、截止时间和执行状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getImprovementTaskPage, addImprovementTask, updateImprovementTask, deleteImprovementTask, processImprovementTask, finishImprovementTask } from '../api'
const api = { page: getImprovementTaskPage, add: addImprovementTask, update: updateImprovementTask, delete: deleteImprovementTask }
const columns = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "departmentName", "label": "责任部门"}, {"prop": "improveItem", "label": "改进事项"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "departmentName", "label": "责任部门"}, {"prop": "improveItem", "label": "改进事项"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "process", "label": "处理", "type": "primary"}, {"command": "finish", "label": "完成", "type": "success"}]
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processImprovementTask(row.id)
  if (command === 'finish') await finishImprovementTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
