<template>
  <DataPage title="报告快照" description="月度成本报告、优化报告和发布归档管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReportSnapshotPage, addReportSnapshot, updateReportSnapshot, deleteReportSnapshot, publishReportSnapshot, archiveReportSnapshot } from '../api'
const api = { page: getReportSnapshotPage, add: addReportSnapshot, update: updateReportSnapshot, delete: deleteReportSnapshot }
const columns = [{"prop": "reportName", "label": "报告名称"}, {"prop": "reportMonth", "label": "报告月份"}, {"prop": "reportType", "label": "报告类型"}, {"prop": "generatedBy", "label": "生成人"}, {"prop": "summaryText", "label": "摘要"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "reportName", "label": "报告名称"}, {"prop": "reportMonth", "label": "报告月份"}, {"prop": "reportType", "label": "报告类型", "type": "select", "options": [{"label": "MONTHLY", "value": "MONTHLY"}, {"label": "OPTIMIZATION", "value": "OPTIMIZATION"}, {"label": "ANOMALY", "value": "ANOMALY"}]}, {"prop": "generatedBy", "label": "生成人"}, {"prop": "summaryText", "label": "摘要", "type": "textarea"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "archive", "label": "归档", "type": "primary"}]
const defaults = {"reportType": "MONTHLY", "status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishReportSnapshot(row.id)
  if (command === 'archive') await archiveReportSnapshot(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
