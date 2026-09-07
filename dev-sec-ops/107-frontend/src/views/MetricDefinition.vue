<template>
  <DataPage title="指标定义" description="指标定义维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>
<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMetricDefinitionPage, addMetricDefinition, updateMetricDefinition, deleteMetricDefinition } from '../api'
const api = { page: getMetricDefinitionPage, add: addMetricDefinition, update: updateMetricDefinition, delete: deleteMetricDefinition }
const columns = [{ prop: 'metricName', label: '指标名称' }, { prop: 'metricCode', label: '指标编码' }, { prop: 'unitName', label: '单位' }, { prop: 'collectInterval', label: '采集间隔' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'metricName', label: '指标名称' }, { prop: 'metricCode', label: '指标编码' }, { prop: 'unitName', label: '单位' }, { prop: 'collectInterval', label: '采集间隔', type: 'number' }, { prop: 'description', label: '说明', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
