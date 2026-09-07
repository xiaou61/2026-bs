<template>
    <DataPage title="部署任务" description="部署任务维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getDeployTaskPage, addDeployTask, updateDeployTask, deleteDeployTask, startDeployTask, finishDeployTask, failDeployTask } from '../api'
  const api = { page: getDeployTaskPage, add: addDeployTask, update: updateDeployTask, delete: deleteDeployTask }
  const columns = [{ prop: 'orderId', label: '发布单ID' }, { prop: 'serviceId', label: '服务ID' }, { prop: 'envId', label: '环境ID' }, { prop: 'taskName', label: '任务名称' }, { prop: 'executor', label: '执行人' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'orderId', label: '发布单ID', type: 'number' }, { prop: 'serviceId', label: '服务ID', type: 'number' }, { prop: 'envId', label: '环境ID', type: 'number' }, { prop: 'taskName', label: '任务名称' }, { prop: 'executor', label: '执行人' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }, { prop: 'startedAt', label: '开始时间' }, { prop: 'finishedAt', label: '结束时间' }, { prop: 'logSummary', label: '日志摘要', type: 'textarea' }]
  const rowActions = [{ label: '启动', command: 'start', type: 'warning' }, { label: '完成', command: 'finish', type: 'warning' }, { label: '失败', command: 'fail', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'start') await startDeployTask(row.id)
if (command === 'finish') await finishDeployTask(row.id)
if (command === 'fail') await failDeployTask(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
