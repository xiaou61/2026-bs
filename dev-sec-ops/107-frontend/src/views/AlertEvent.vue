<template>
    <DataPage title="告警事件" description="告警事件维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'OPEN' }" @row-action="handleAction" />
  </template>
  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getAlertEventPage, addAlertEvent, updateAlertEvent, deleteAlertEvent, ackAlertEvent, resolveAlertEvent, closeAlertEvent } from '../api'
  const api = { page: getAlertEventPage, add: addAlertEvent, update: updateAlertEvent, delete: deleteAlertEvent }
  const columns = [{ prop: 'eventNo', label: '事件编号' }, { prop: 'serverId', label: '主机ID' }, { prop: 'metricCode', label: '指标编码' }, { prop: 'currentValue', label: '当前值' }, { prop: 'severity', label: '级别' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'eventNo', label: '事件编号' }, { prop: 'serverId', label: '主机ID', type: 'number' }, { prop: 'ruleId', label: '规则ID', type: 'number' }, { prop: 'metricCode', label: '指标编码' }, { prop: 'currentValue', label: '当前值' }, { prop: 'severity', label: '级别' }, { prop: 'ownerName', label: '负责人' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }, { prop: 'triggeredAt', label: '触发时间' }, { prop: 'resolvedAt', label: '恢复时间' }]
  const rowActions = [{ label: '确认', command: 'ack', type: 'warning' }, { label: '恢复', command: 'resolve', type: 'warning' }, { label: '关闭', command: 'close', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'ack') await ackAlertEvent(row.id)
if (command === 'resolve') await resolveAlertEvent(row.id)
if (command === 'close') await closeAlertEvent(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
