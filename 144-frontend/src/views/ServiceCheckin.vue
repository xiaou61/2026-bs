<template>
  <DataPage title="开题材料" description="材料编号、课题编号、材料名称、路径类型、提交学生和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getServiceCheckinPage, addServiceCheckin, updateServiceCheckin, deleteServiceCheckin, activateServiceCheckin, finishServiceCheckin } from '../api'
const api = { page: getServiceCheckinPage, add: addServiceCheckin, update: updateServiceCheckin, delete: deleteServiceCheckin }
const columns = [{"prop": "achievementNo", "label": "材料编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "achievementName", "label": "材料名称"}, {"prop": "achievementType", "label": "路径类型"}, {"prop": "ownerName", "label": "提交学生"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "achievementNo", "label": "材料编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "achievementName", "label": "材料名称"}, {"prop": "achievementType", "label": "路径类型"}, {"prop": "ownerName", "label": "提交学生"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}]
const defaults = {"status": "PUBLISHED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateServiceCheckin(row.id)
  if (command === 'finish') await finishServiceCheckin(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>






