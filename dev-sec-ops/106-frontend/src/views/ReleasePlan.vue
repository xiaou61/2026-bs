<template>
    <DataPage title="发布计划" description="发布计划维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getReleasePlanPage, addReleasePlan, updateReleasePlan, deleteReleasePlan, publishReleasePlan, archiveReleasePlan } from '../api'
  const api = { page: getReleasePlanPage, add: addReleasePlan, update: updateReleasePlan, delete: deleteReleasePlan }
  const columns = [{ prop: 'planNo', label: '计划编号' }, { prop: 'planTitle', label: '计划标题' }, { prop: 'serviceId', label: '服务ID' }, { prop: 'envId', label: '环境ID' }, { prop: 'riskLevel', label: '风险等级' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'planNo', label: '计划编号' }, { prop: 'planTitle', label: '计划标题' }, { prop: 'serviceId', label: '服务ID', type: 'number' }, { prop: 'envId', label: '环境ID', type: 'number' }, { prop: 'releaseWindow', label: '发布窗口' }, { prop: 'riskLevel', label: '风险等级' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }, { prop: 'description', label: '说明', type: 'textarea' }]
  const rowActions = [{ label: '发布', command: 'publish', type: 'warning' }, { label: '归档', command: 'archive', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'publish') await publishReleasePlan(row.id)
if (command === 'archive') await archiveReleasePlan(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
