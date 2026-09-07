<template>
  <DataPage title="审计报告" description="审计报告数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAuditReportPage, addAuditReport, updateAuditReport, deleteAuditReport, publishAuditReport, archiveAuditReport } from '../api'
const api = { page: getAuditReportPage, add: addAuditReport, update: updateAuditReport, delete: deleteAuditReport }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'reportNo', label: '报告编号' }, { prop: 'repositoryId', label: '项目ID' }, { prop: 'reportTitle', label: '报告标题' }, { prop: 'status', label: '状态' }, { prop: 'publishedAt', label: '发布时间' }]
const formFields = [{ prop: 'reportNo', label: '报告编号' }, { prop: 'repositoryId', label: '项目ID', type: 'number' }, { prop: 'taskId', label: '任务ID', type: 'number' }, { prop: 'reportTitle', label: '报告标题' }, { prop: 'riskSummary', label: '风险摘要', type: 'textarea' }, { prop: 'conclusion', label: '结论', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }, { prop: 'publishedAt', label: '发布时间' }]
const rowActions = [{ label: '发布', command: 'publish', type: 'warning' }, { label: '归档', command: 'archive', type: 'warning' }]

const handleAction = async ({ command, row, refresh }) => {
    if (command === 'publish') await publishAuditReport(row.id)
if (command === 'archive') await archiveAuditReport(row.id)
ElMessage.success('状态已更新')
  refresh()
}

</script>
