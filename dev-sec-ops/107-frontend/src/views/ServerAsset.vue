<template>
    <DataPage title="主机资产" description="主机资产维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ONLINE' }" @row-action="handleAction" />
  </template>
  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getServerAssetPage, addServerAsset, updateServerAsset, deleteServerAsset, onlineServerAsset, offlineServerAsset } from '../api'
  const api = { page: getServerAssetPage, add: addServerAsset, update: updateServerAsset, delete: deleteServerAsset }
  const columns = [{ prop: 'serverName', label: '主机名称' }, { prop: 'instanceId', label: '实例ID' }, { prop: 'publicIp', label: '公网IP' }, { prop: 'privateIp', label: '私网IP' }, { prop: 'cpuCores', label: 'CPU核数' }, { prop: 'memoryGb', label: '内存GB' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'serverName', label: '主机名称' }, { prop: 'instanceId', label: '实例ID', type: 'number' }, { prop: 'regionId', label: '区域ID', type: 'number' }, { prop: 'publicIp', label: '公网IP' }, { prop: 'privateIp', label: '私网IP' }, { prop: 'osName', label: '操作系统' }, { prop: 'cpuCores', label: 'CPU核数', type: 'number' }, { prop: 'memoryGb', label: '内存GB', type: 'number' }, { prop: 'ownerName', label: '负责人' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }]
  const rowActions = [{ label: '上线', command: 'online', type: 'warning' }, { label: '下线', command: 'offline', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'online') await onlineServerAsset(row.id)
if (command === 'offline') await offlineServerAsset(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
