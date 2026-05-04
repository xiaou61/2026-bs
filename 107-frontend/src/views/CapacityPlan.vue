<template>
    <DataPage title="容量规划" description="容量规划维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>
  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getCapacityPlanPage, addCapacityPlan, updateCapacityPlan, deleteCapacityPlan, publishCapacityPlan, archiveCapacityPlan } from '../api'
  const api = { page: getCapacityPlanPage, add: addCapacityPlan, update: updateCapacityPlan, delete: deleteCapacityPlan }
  const columns = [{ prop: 'serverId', label: '主机ID' }, { prop: 'planName', label: '规划名称' }, { prop: 'cpuUsage', label: 'CPU使用率' }, { prop: 'memoryUsage', label: '内存使用率' }, { prop: 'diskUsage', label: '磁盘使用率' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'serverId', label: '主机ID', type: 'number' }, { prop: 'planName', label: '规划名称' }, { prop: 'cpuUsage', label: 'CPU使用率' }, { prop: 'memoryUsage', label: '内存使用率' }, { prop: 'diskUsage', label: '磁盘使用率' }, { prop: 'suggestion', label: '优化建议', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }]
  const rowActions = [{ label: '发布', command: 'publish', type: 'warning' }, { label: '归档', command: 'archive', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'publish') await publishCapacityPlan(row.id)
if (command === 'archive') await archiveCapacityPlan(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
