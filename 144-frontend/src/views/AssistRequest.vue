<template>
  <DataPage title="课题申请" description="志愿者编号、课题编号、申请学生、志愿顺序、加入时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAssistRequestPage, addAssistRequest, updateAssistRequest, deleteAssistRequest, submitAssistRequest, approveAssistRequest } from '../api'
const api = { page: getAssistRequestPage, add: addAssistRequest, update: updateAssistRequest, delete: deleteAssistRequest }
const columns = [{"prop": "claimNo", "label": "志愿者编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "applicantName", "label": "申请学生"}, {"prop": "claimAmount", "label": "志愿顺序"}, {"prop": "claimTime", "label": "加入时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "claimNo", "label": "志愿者编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "applicantName", "label": "申请学生"}, {"prop": "claimAmount", "label": "志愿顺序", "type": "number"}, {"prop": "claimTime", "label": "加入时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitAssistRequest(row.id)
  if (command === 'approve') await approveAssistRequest(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>






