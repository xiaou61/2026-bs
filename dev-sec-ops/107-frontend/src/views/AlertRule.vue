<template>
    <DataPage title="告警规则" description="告警规则维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>
  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getAlertRulePage, addAlertRule, updateAlertRule, deleteAlertRule, enableAlertRule, disableAlertRule } from '../api'
  const api = { page: getAlertRulePage, add: addAlertRule, update: updateAlertRule, delete: deleteAlertRule }
  const columns = [{ prop: 'ruleName', label: '规则名称' }, { prop: 'metricCode', label: '指标编码' }, { prop: 'operatorType', label: '比较符' }, { prop: 'thresholdValue', label: '阈值' }, { prop: 'severity', label: '级别' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'ruleName', label: '规则名称' }, { prop: 'metricCode', label: '指标编码' }, { prop: 'operatorType', label: '比较符' }, { prop: 'thresholdValue', label: '阈值' }, { prop: 'durationMinute', label: '持续分钟', type: 'number' }, { prop: 'severity', label: '级别' }, { prop: 'notifyGroup', label: '通知组' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }]
  const rowActions = [{ label: '启用', command: 'enable', type: 'warning' }, { label: '停用', command: 'disable', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'enable') await enableAlertRule(row.id)
if (command === 'disable') await disableAlertRule(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
