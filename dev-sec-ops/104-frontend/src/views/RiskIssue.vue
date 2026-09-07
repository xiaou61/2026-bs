<template>
  <DataPage title="风险问题" description="风险问题数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'OPEN', severity: 'MEDIUM' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRiskIssuePage, addRiskIssue, updateRiskIssue, deleteRiskIssue, confirmRiskIssue, ignoreRiskIssue } from '../api'
const api = { page: getRiskIssuePage, add: addRiskIssue, update: updateRiskIssue, delete: deleteRiskIssue }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'issueNo', label: '问题编号' }, { prop: 'issueTitle', label: '问题标题' }, { prop: 'severity', label: '严重程度' }, { prop: 'issueType', label: '问题类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'issueNo', label: '问题编号' }, { prop: 'repositoryId', label: '项目ID', type: 'number' }, { prop: 'resultId', label: '结果ID', type: 'number' }, { prop: 'issueTitle', label: '问题标题' }, { prop: 'severity', label: '严重程度' }, { prop: 'issueType', label: '问题类型' }, { prop: 'ownerName', label: '负责人' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }, { prop: 'dueDate', label: '截止日期' }, { prop: 'description', label: '问题说明', type: 'textarea' }]
const rowActions = [{ label: '确认', command: 'confirm', type: 'warning' }, { label: '忽略', command: 'ignore', type: 'warning' }]

const handleAction = async ({ command, row, refresh }) => {
    if (command === 'confirm') await confirmRiskIssue(row.id)
if (command === 'ignore') await ignoreRiskIssue(row.id)
ElMessage.success('状态已更新')
  refresh()
}

</script>
