<template>
    <DataPage title="结果明细" description="结果明细维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getExecutionResultPage, addExecutionResult, updateExecutionResult, deleteExecutionResult, confirmExecutionResult, rejectExecutionResult } from '../api'
  const api = { page: getExecutionResultPage, add: addExecutionResult, update: updateExecutionResult, delete: deleteExecutionResult }
  const columns = [{ prop: 'executionId', label: '执行ID' }, { prop: 'caseId', label: '用例ID' }, { prop: 'resultStatus', label: '结果状态' }, { prop: 'actualCode', label: '实际状态码' }, { prop: 'durationMs', label: '耗时毫秒' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'executionId', label: '执行ID', type: 'number' }, { prop: 'caseId', label: '用例ID', type: 'number' }, { prop: 'endpointId', label: '接口ID', type: 'number' }, { prop: 'resultStatus', label: '结果状态' }, { prop: 'actualCode', label: '实际状态码', type: 'number' }, { prop: 'durationMs', label: '耗时毫秒', type: 'number' }, { prop: 'assertMessage', label: '断言信息', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
  const rowActions = [{ label: '确认', command: 'confirm', type: 'warning' }, { label: '驳回', command: 'reject', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'confirm') await confirmExecutionResult(row.id)
if (command === 'reject') await rejectExecutionResult(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
