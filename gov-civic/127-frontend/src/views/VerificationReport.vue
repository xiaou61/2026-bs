<template>
  <DataPage title="核查报告" description="核查报告、企业、报告月份、核查员和结论维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getVerificationReportPage, addVerificationReport, updateVerificationReport, deleteVerificationReport, submitVerificationReport, approveVerificationReport } from '../api'
const api = { page: getVerificationReportPage, add: addVerificationReport, update: updateVerificationReport, delete: deleteVerificationReport }
const columns = [{"prop": "reportNo", "label": "报告编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "reportMonth", "label": "报告月份"}, {"prop": "auditorName", "label": "核查员"}, {"prop": "conclusionText", "label": "核查结论"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "reportNo", "label": "报告编号"}, {"prop": "companyNo", "label": "企业编号"}, {"prop": "reportMonth", "label": "报告月份"}, {"prop": "auditorName", "label": "核查员"}, {"prop": "conclusionText", "label": "核查结论"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitVerificationReport(row.id)
  if (command === 'approve') await approveVerificationReport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
