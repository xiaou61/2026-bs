<template>
  <DataPage title="报告导出" description="报告导出、公司、报告月份、导出格式和导出状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReportExportPage, addReportExport, updateReportExport, deleteReportExport, submitReportExport, approveReportExport } from '../api'
const api = { page: getReportExportPage, add: addReportExport, update: updateReportExport, delete: deleteReportExport }
const columns = [{"prop": "exportNo", "label": "导出编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "reportMonth", "label": "报告月份"}, {"prop": "formatType", "label": "导出格式"}, {"prop": "operatorName", "label": "操作人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "exportNo", "label": "导出编号"}, {"prop": "companyName", "label": "公司名称"}, {"prop": "reportMonth", "label": "报告月份"}, {"prop": "formatType", "label": "导出格式"}, {"prop": "operatorName", "label": "操作人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "ACTIVE", "value": "ACTIVE"}, {"label": "DISABLED", "value": "DISABLED"}, {"label": "DRAFT", "value": "DRAFT"}, {"label": "SUBMITTED", "value": "SUBMITTED"}, {"label": "REVIEWING", "value": "REVIEWING"}, {"label": "APPROVED", "value": "APPROVED"}, {"label": "REJECTED", "value": "REJECTED"}, {"label": "OPEN", "value": "OPEN"}, {"label": "PROCESSING", "value": "PROCESSING"}, {"label": "FINISHED", "value": "FINISHED"}, {"label": "WARNING", "value": "WARNING"}, {"label": "PUBLISHED", "value": "PUBLISHED"}]}]
const rowActions = [{"command": "submit", "label": "提交", "type": "primary"}, {"command": "approve", "label": "通过", "type": "success"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitReportExport(row.id)
  if (command === 'approve') await approveReportExport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
