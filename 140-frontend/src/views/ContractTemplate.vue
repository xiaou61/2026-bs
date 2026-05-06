<template>
  <DataPage title="课题发布" description="课题编号、课题名称、导师姓名、所属方向、计划容量和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getContractTemplatePage, addContractTemplate, updateContractTemplate, deleteContractTemplate, activateContractTemplate, finishContractTemplate } from '../api'
const api = { page: getContractTemplatePage, add: addContractTemplate, update: updateContractTemplate, delete: deleteContractTemplate }
const columns = [{"prop": "projectNo", "label": "课题编号"}, {"prop": "projectName", "label": "课题名称"}, {"prop": "leaderName", "label": "导师姓名"}, {"prop": "collegeName", "label": "所属方向"}, {"prop": "startYear", "label": "计划容量"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "projectNo", "label": "课题编号"}, {"prop": "projectName", "label": "课题名称"}, {"prop": "leaderName", "label": "导师姓名"}, {"prop": "collegeName", "label": "所属方向"}, {"prop": "startYear", "label": "计划容量", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "ACTIVE"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateContractTemplate(row.id)
  if (command === 'finish') await finishContractTemplate(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




