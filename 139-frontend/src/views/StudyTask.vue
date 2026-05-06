<template>
  <DataPage title="审稿专家" description="专家编号、专家姓名、研究方向、所在单位、邀请时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getStudyTaskPage, addStudyTask, updateStudyTask, deleteStudyTask, submitStudyTask, approveStudyTask } from '../api'
const api = { page: getStudyTaskPage, add: addStudyTask, update: updateStudyTask, delete: deleteStudyTask }
const columns = [{"prop": "requestNo", "label": "专家编号"}, {"prop": "consumableName", "label": "专家姓名"}, {"prop": "requestQty", "label": "评审负载"}, {"prop": "applicantName", "label": "研究方向"}, {"prop": "requestTime", "label": "邀请时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "requestNo", "label": "专家编号"}, {"prop": "consumableName", "label": "专家姓名"}, {"prop": "requestQty", "label": "评审负载", "type": "number"}, {"prop": "applicantName", "label": "研究方向"}, {"prop": "requestTime", "label": "邀请时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitStudyTask(row.id)
  if (command === 'approve') await approveStudyTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




