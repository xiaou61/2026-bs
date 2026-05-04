<template>
  <DataPage title="通知记录" description="通知记录维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>
<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getAlertNotifyPage, addAlertNotify, updateAlertNotify, deleteAlertNotify } from '../api'
const api = { page: getAlertNotifyPage, add: addAlertNotify, update: updateAlertNotify, delete: deleteAlertNotify }
const columns = [{ prop: 'eventId', label: '事件ID' }, { prop: 'channelName', label: '通知渠道' }, { prop: 'receiver', label: '接收人' }, { prop: 'sendStatus', label: '发送状态' }, { prop: 'sendTime', label: '发送时间' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'eventId', label: '事件ID', type: 'number' }, { prop: 'channelName', label: '通知渠道' }, { prop: 'receiver', label: '接收人' }, { prop: 'sendStatus', label: '发送状态' }, { prop: 'sendTime', label: '发送时间' }, { prop: 'messageText', label: '消息内容', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
