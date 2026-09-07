<template>
    <DataPage title="处置工单" description="处置工单维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>
  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getIncidentTicketPage, addIncidentTicket, updateIncidentTicket, deleteIncidentTicket, assignIncidentTicket, resolveIncidentTicket, closeIncidentTicket } from '../api'
  const api = { page: getIncidentTicketPage, add: addIncidentTicket, update: updateIncidentTicket, delete: deleteIncidentTicket }
  const columns = [{ prop: 'ticketNo', label: '工单编号' }, { prop: 'eventId', label: '事件ID' }, { prop: 'title', label: '工单标题' }, { prop: 'assignee', label: '处理人' }, { prop: 'priority', label: '优先级' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'ticketNo', label: '工单编号' }, { prop: 'eventId', label: '事件ID', type: 'number' }, { prop: 'serverId', label: '主机ID', type: 'number' }, { prop: 'title', label: '工单标题' }, { prop: 'assignee', label: '处理人' }, { prop: 'priority', label: '优先级' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }, { prop: 'solution', label: '解决方案', type: 'textarea' }, { prop: 'closedAt', label: '关闭时间' }]
  const rowActions = [{ label: '分配', command: 'assign', type: 'warning' }, { label: '解决', command: 'resolve', type: 'warning' }, { label: '关闭', command: 'close', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'assign') await assignIncidentTicket(row.id)
if (command === 'resolve') await resolveIncidentTicket(row.id)
if (command === 'close') await closeIncidentTicket(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
