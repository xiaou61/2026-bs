<template>
  <DataPage title="审计报告" description="隐私授权、访问日志和风险处置审计报告管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAuditReportPage, addAuditReport, updateAuditReport, deleteAuditReport, publishAuditReport, archiveAuditReport } from '../api'
const api = { page: getAuditReportPage, add: addAuditReport, update: updateAuditReport, delete: deleteAuditReport }
const columns = [{"prop": "reportNo", "label": "报告编号"}, {"prop": "reportName", "label": "报告名称"}, {"prop": "reportPeriod", "label": "报告周期"}, {"prop": "generatedBy", "label": "生成人"}, {"prop": "summaryText", "label": "摘要"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "reportNo", "label": "报告编号"}, {"prop": "reportName", "label": "报告名称"}, {"prop": "reportPeriod", "label": "报告周期"}, {"prop": "generatedBy", "label": "生成人"}, {"prop": "summaryText", "label": "摘要", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "archive", "label": "归档", "type": "primary"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishAuditReport(row.id)
  if (command === 'archive') await archiveAuditReport(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
