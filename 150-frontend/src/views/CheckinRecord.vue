<template>
  <DataPage title="双选确认" description="确认编号、检查技师编号、确认结果、导师姓名、确认意见和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCheckinRecordPage, addCheckinRecord, updateCheckinRecord, deleteCheckinRecord, submitCheckinRecord, approveCheckinRecord } from '../api'
const api = { page: getCheckinRecordPage, add: addCheckinRecord, update: updateCheckinRecord, delete: deleteCheckinRecord }
const columns = [{"prop": "approvalNo", "label": "确认编号"}, {"prop": "claimNo", "label": "检查技师编号"}, {"prop": "nodeName", "label": "确认结果"}, {"prop": "approverName", "label": "导师姓名"}, {"prop": "approvalOpinion", "label": "确认意见"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "approvalNo", "label": "确认编号"}, {"prop": "claimNo", "label": "检查技师编号"}, {"prop": "nodeName", "label": "确认结果"}, {"prop": "approverName", "label": "导师姓名"}, {"prop": "approvalOpinion", "label": "确认意见", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}, {"label": "SUCCESS", "value": "SUCCESS"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "REVIEWING"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitCheckinRecord(row.id)
  if (command === 'approve') await approveCheckinRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>









