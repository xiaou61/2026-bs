<template>
  <DataPage title="企业填报" description="企业填报单、公司、填报人、提交时间和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getCompanySubmissionPage, addCompanySubmission, updateCompanySubmission, deleteCompanySubmission, submitCompanySubmission, approveCompanySubmission } from '../api'
const api = { page: getCompanySubmissionPage, add: addCompanySubmission, update: updateCompanySubmission, delete: deleteCompanySubmission }
const columns = [{"prop": "submissionNo", "label": "填报编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "periodMonth", "label": "报告月份"}, {"prop": "fillerName", "label": "填报人"}, {"prop": "submitTime", "label": "提交时间"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "submissionNo", "label": "填报编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "periodMonth", "label": "报告月份"}, {"prop": "fillerName", "label": "填报人"}, {"prop": "submitTime", "label": "提交时间"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "SUBMITTED"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitCompanySubmission(row.id)
  if (command === 'approve') await approveCompanySubmission(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
