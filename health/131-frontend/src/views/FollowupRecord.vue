<template>
  <DataPage title="随访记录" description="记录编号、计划编号、随访时间、患者情况和记录人维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getFollowupRecordPage, addFollowupRecord, updateFollowupRecord, deleteFollowupRecord, submitFollowupRecord, approveFollowupRecord } from '../api'
const api = { page: getFollowupRecordPage, add: addFollowupRecord, update: updateFollowupRecord, delete: deleteFollowupRecord }
const columns = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "planNo", "label": "计划编号"}, {"prop": "followTime", "label": "随访时间"}, {"prop": "patientCondition", "label": "患者情况"}, {"prop": "recorderName", "label": "记录人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "recordNo", "label": "记录编号"}, {"prop": "planNo", "label": "计划编号"}, {"prop": "followTime", "label": "随访时间"}, {"prop": "patientCondition", "label": "患者情况"}, {"prop": "recorderName", "label": "记录人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitFollowupRecord(row.id)
  if (command === 'approve') await approveFollowupRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
