<template>
  <DataPage title="不良反应上报" description="上报编号、患者、药品、上报时间、严重等级和处理状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAdverseReportPage, addAdverseReport, updateAdverseReport, deleteAdverseReport, submitAdverseReport, approveAdverseReport } from '../api'
const api = { page: getAdverseReportPage, add: addAdverseReport, update: updateAdverseReport, delete: deleteAdverseReport }
const columns = [{"prop": "reportNo", "label": "上报编号"}, {"prop": "patientNo", "label": "患者编号"}, {"prop": "drugName", "label": "药品名称"}, {"prop": "reportTime", "label": "上报时间"}, {"prop": "severityLevel", "label": "严重等级"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "reportNo", "label": "上报编号"}, {"prop": "patientNo", "label": "患者编号"}, {"prop": "drugName", "label": "药品名称"}, {"prop": "reportTime", "label": "上报时间"}, {"prop": "severityLevel", "label": "严重等级"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "NORMAL", "value": "NORMAL"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitAdverseReport(row.id)
  if (command === 'approve') await approveAdverseReport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
