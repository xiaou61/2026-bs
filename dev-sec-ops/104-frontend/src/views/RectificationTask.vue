<template>
  <DataPage title="整改任务" description="整改任务数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRectificationTaskPage, addRectificationTask, updateRectificationTask, deleteRectificationTask, assignRectificationTask, finishRectificationTask, reopenRectificationTask } from '../api'
const api = { page: getRectificationTaskPage, add: addRectificationTask, update: updateRectificationTask, delete: deleteRectificationTask }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'issueId', label: '问题ID' }, { prop: 'assignee', label: '处理人' }, { prop: 'actionPlan', label: '整改方案' }, { prop: 'status', label: '状态' }, { prop: 'dueDate', label: '截止日期' }]
const formFields = [{ prop: 'issueId', label: '问题ID', type: 'number' }, { prop: 'assignee', label: '处理人' }, { prop: 'actionPlan', label: '整改方案', type: 'textarea' }, { prop: 'progressNote', label: '进展说明', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }, { prop: 'dueDate', label: '截止日期' }, { prop: 'finishedAt', label: '完成时间' }]
const rowActions = [{ label: '分配', command: 'assign', type: 'warning' }, { label: '完成', command: 'finish', type: 'warning' }, { label: '重开', command: 'reopen', type: 'warning' }]

const handleAction = async ({ command, row, refresh }) => {
    if (command === 'assign') await assignRectificationTask(row.id)
if (command === 'finish') await finishRectificationTask(row.id)
if (command === 'reopen') await reopenRectificationTask(row.id)
ElMessage.success('状态已更新')
  refresh()
}

</script>
