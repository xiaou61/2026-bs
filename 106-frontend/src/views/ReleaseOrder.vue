<template>
    <DataPage title="发布单" description="发布单维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'DRAFT' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getReleaseOrderPage, addReleaseOrder, updateReleaseOrder, deleteReleaseOrder, submitReleaseOrder, approveReleaseOrder, rejectReleaseOrder, scheduleReleaseOrder } from '../api'
  const api = { page: getReleaseOrderPage, add: addReleaseOrder, update: updateReleaseOrder, delete: deleteReleaseOrder }
  const columns = [{ prop: 'orderNo', label: '发布单号' }, { prop: 'planId', label: '计划ID' }, { prop: 'serviceId', label: '服务ID' }, { prop: 'versionNo', label: '版本号' }, { prop: 'applicant', label: '申请人' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'orderNo', label: '发布单号' }, { prop: 'planId', label: '计划ID', type: 'number' }, { prop: 'serviceId', label: '服务ID', type: 'number' }, { prop: 'versionNo', label: '版本号' }, { prop: 'applicant', label: '申请人' }, { prop: 'releaseScope', label: '发布范围' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }, { prop: 'scheduledAt', label: '计划时间' }, { prop: 'summary', label: '摘要', type: 'textarea' }]
  const rowActions = [{ label: '提交', command: 'submit', type: 'warning' }, { label: '通过', command: 'approve', type: 'warning' }, { label: '驳回', command: 'reject', type: 'warning' }, { label: '排期', command: 'schedule', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'submit') await submitReleaseOrder(row.id)
if (command === 'approve') await approveReleaseOrder(row.id)
if (command === 'reject') await rejectReleaseOrder(row.id)
if (command === 'schedule') await scheduleReleaseOrder(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
