<template>
    <DataPage title="回滚记录" description="回滚记录维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getRollbackRecordPage, addRollbackRecord, updateRollbackRecord, deleteRollbackRecord, startRollbackRecord, finishRollbackRecord, failRollbackRecord } from '../api'
  const api = { page: getRollbackRecordPage, add: addRollbackRecord, update: updateRollbackRecord, delete: deleteRollbackRecord }
  const columns = [{ prop: 'orderId', label: '发布单ID' }, { prop: 'rollbackNo', label: '回滚编号' }, { prop: 'operatorName', label: '操作人' }, { prop: 'reason', label: '回滚原因' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'orderId', label: '发布单ID', type: 'number' }, { prop: 'planId', label: '预案ID', type: 'number' }, { prop: 'rollbackNo', label: '回滚编号' }, { prop: 'operatorName', label: '操作人' }, { prop: 'reason', label: '回滚原因', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }, { prop: 'startedAt', label: '开始时间' }, { prop: 'finishedAt', label: '结束时间' }]
  const rowActions = [{ label: '启动', command: 'start', type: 'warning' }, { label: '完成', command: 'finish', type: 'warning' }, { label: '失败', command: 'fail', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'start') await startRollbackRecord(row.id)
if (command === 'finish') await finishRollbackRecord(row.id)
if (command === 'fail') await failRollbackRecord(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
