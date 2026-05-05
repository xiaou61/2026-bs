<template>
  <DataPage title="报告周期" description="报告周期、公司、报告月份、模板和填报状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReportingPeriodPage, addReportingPeriod, updateReportingPeriod, deleteReportingPeriod, submitReportingPeriod, approveReportingPeriod } from '../api'
const api = { page: getReportingPeriodPage, add: addReportingPeriod, update: updateReportingPeriod, delete: deleteReportingPeriod }
const columns = [{"prop": "periodNo", "label": "周期编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "reportMonth", "label": "报告月份"}, {"prop": "templateName", "label": "模板名称"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "periodNo", "label": "周期编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "reportMonth", "label": "报告月份"}, {"prop": "templateName", "label": "模板名称"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitReportingPeriod(row.id)
  if (command === 'approve') await approveReportingPeriod(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
