<template>
  <DataPage title="课题申请" description="申请编号、课题编号、申请学生、志愿顺序、申请时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getExamSessionPage, addExamSession, updateExamSession, deleteExamSession, submitExamSession, approveExamSession } from '../api'
const api = { page: getExamSessionPage, add: addExamSession, update: updateExamSession, delete: deleteExamSession }
const columns = [{"prop": "claimNo", "label": "申请编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "applicantName", "label": "申请学生"}, {"prop": "claimAmount", "label": "志愿顺序"}, {"prop": "claimTime", "label": "申请时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "claimNo", "label": "申请编号"}, {"prop": "projectNo", "label": "课题编号"}, {"prop": "applicantName", "label": "申请学生"}, {"prop": "claimAmount", "label": "志愿顺序", "type": "number"}, {"prop": "claimTime", "label": "申请时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitExamSession(row.id)
  if (command === 'approve') await approveExamSession(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>



