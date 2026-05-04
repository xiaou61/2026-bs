<template>
    <DataPage title="审批记录" description="审批记录维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getApprovalRecordPage, addApprovalRecord, updateApprovalRecord, deleteApprovalRecord, approveApprovalRecord, rejectApprovalRecord } from '../api'
  const api = { page: getApprovalRecordPage, add: addApprovalRecord, update: updateApprovalRecord, delete: deleteApprovalRecord }
  const columns = [{ prop: 'orderId', label: '发布单ID' }, { prop: 'nodeName', label: '节点名称' }, { prop: 'applicant', label: '申请人' }, { prop: 'approver', label: '审批人' }, { prop: 'decision', label: '审批结果' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'orderId', label: '发布单ID', type: 'number' }, { prop: 'nodeName', label: '节点名称' }, { prop: 'applicant', label: '申请人' }, { prop: 'approver', label: '审批人' }, { prop: 'decision', label: '审批结果' }, { prop: 'opinion', label: '审批意见', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
  const rowActions = [{ label: '通过', command: 'approve', type: 'warning' }, { label: '驳回', command: 'reject', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'approve') await approveApprovalRecord(row.id)
if (command === 'reject') await rejectApprovalRecord(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
