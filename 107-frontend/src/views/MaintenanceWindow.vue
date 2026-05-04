<template>
    <DataPage title="维护窗口" description="维护窗口维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>
  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getMaintenanceWindowPage, addMaintenanceWindow, updateMaintenanceWindow, deleteMaintenanceWindow, startMaintenanceWindow, finishMaintenanceWindow } from '../api'
  const api = { page: getMaintenanceWindowPage, add: addMaintenanceWindow, update: updateMaintenanceWindow, delete: deleteMaintenanceWindow }
  const columns = [{ prop: 'windowName', label: '窗口名称' }, { prop: 'serverId', label: '主机ID' }, { prop: 'startTime', label: '开始时间' }, { prop: 'endTime', label: '结束时间' }, { prop: 'ownerName', label: '负责人' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'windowName', label: '窗口名称' }, { prop: 'serverId', label: '主机ID', type: 'number' }, { prop: 'startTime', label: '开始时间' }, { prop: 'endTime', label: '结束时间' }, { prop: 'ownerName', label: '负责人' }, { prop: 'reason', label: '维护原因', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }]
  const rowActions = [{ label: '开始', command: 'start', type: 'warning' }, { label: '完成', command: 'finish', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'start') await startMaintenanceWindow(row.id)
if (command === 'finish') await finishMaintenanceWindow(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
