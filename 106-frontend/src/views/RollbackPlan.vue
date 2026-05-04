<template>
    <DataPage title="回滚预案" description="回滚预案维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getRollbackPlanPage, addRollbackPlan, updateRollbackPlan, deleteRollbackPlan, activateRollbackPlan, archiveRollbackPlan } from '../api'
  const api = { page: getRollbackPlanPage, add: addRollbackPlan, update: updateRollbackPlan, delete: deleteRollbackPlan }
  const columns = [{ prop: 'planId', label: '计划ID' }, { prop: 'serviceId', label: '服务ID' }, { prop: 'rollbackVersion', label: '回滚版本' }, { prop: 'strategyName', label: '策略名称' }, { prop: 'ownerName', label: '负责人' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'planId', label: '计划ID', type: 'number' }, { prop: 'serviceId', label: '服务ID', type: 'number' }, { prop: 'rollbackVersion', label: '回滚版本' }, { prop: 'strategyName', label: '策略名称' }, { prop: 'triggerCondition', label: '触发条件', type: 'textarea' }, { prop: 'ownerName', label: '负责人' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
  const rowActions = [{ label: '启用', command: 'activate', type: 'warning' }, { label: '归档', command: 'archive', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'activate') await activateRollbackPlan(row.id)
if (command === 'archive') await archiveRollbackPlan(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
