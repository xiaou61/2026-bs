<template>
    <DataPage title="执行记录" description="执行记录维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'DRAFT', envName: '测试环境' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getTestExecutionPage, addTestExecution, updateTestExecution, deleteTestExecution, startTestExecution, finishTestExecution, failTestExecution } from '../api'
  const api = { page: getTestExecutionPage, add: addTestExecution, update: updateTestExecution, delete: deleteTestExecution }
  const columns = [{ prop: 'executionNo', label: '执行编号' }, { prop: 'projectId', label: '项目ID' }, { prop: 'envName', label: '执行环境' }, { prop: 'executor', label: '执行人' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'executionNo', label: '执行编号' }, { prop: 'projectId', label: '项目ID', type: 'number' }, { prop: 'envName', label: '执行环境' }, { prop: 'executor', label: '执行人' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }, { prop: 'startedAt', label: '开始时间' }, { prop: 'finishedAt', label: '结束时间' }, { prop: 'summary', label: '执行摘要', type: 'textarea' }]
  const rowActions = [{ label: '启动', command: 'start', type: 'warning' }, { label: '完成', command: 'finish', type: 'warning' }, { label: '失败', command: 'fail', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'start') await startTestExecution(row.id)
if (command === 'finish') await finishTestExecution(row.id)
if (command === 'fail') await failTestExecution(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
