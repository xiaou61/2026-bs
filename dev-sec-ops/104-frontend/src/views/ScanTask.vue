<template>
  <DataPage title="扫描任务" description="扫描任务数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'DRAFT', triggerType: 'MANUAL', scanMode: 'FULL' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getScanTaskPage, addScanTask, updateScanTask, deleteScanTask, startScanTask, finishScanTask, failScanTask } from '../api'
const api = { page: getScanTaskPage, add: addScanTask, update: updateScanTask, delete: deleteScanTask }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'taskNo', label: '任务编号' }, { prop: 'repositoryId', label: '项目ID' }, { prop: 'branchName', label: '扫描分支' }, { prop: 'triggerType', label: '触发方式' }, { prop: 'scanMode', label: '扫描模式' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'taskNo', label: '任务编号' }, { prop: 'repositoryId', label: '项目ID', type: 'number' }, { prop: 'branchName', label: '扫描分支' }, { prop: 'triggerType', label: '触发方式' }, { prop: 'scanMode', label: '扫描模式' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }, { prop: 'startedAt', label: '开始时间' }, { prop: 'finishedAt', label: '结束时间' }, { prop: 'summary', label: '扫描摘要', type: 'textarea' }]
const rowActions = [{ label: '启动', command: 'start', type: 'warning' }, { label: '完成', command: 'finish', type: 'warning' }, { label: '失败', command: 'fail', type: 'warning' }]

const handleAction = async ({ command, row, refresh }) => {
    if (command === 'start') await startScanTask(row.id)
if (command === 'finish') await finishScanTask(row.id)
if (command === 'fail') await failScanTask(row.id)
ElMessage.success('状态已更新')
  refresh()
}

</script>
