<template>
  <DataPage title="指标采样" description="指标采样维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>
<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMetricSamplePage, addMetricSample, updateMetricSample, deleteMetricSample } from '../api'
const api = { page: getMetricSamplePage, add: addMetricSample, update: updateMetricSample, delete: deleteMetricSample }
const columns = [{ prop: 'serverId', label: '主机ID' }, { prop: 'metricCode', label: '指标编码' }, { prop: 'metricValue', label: '指标值' }, { prop: 'sampleTime', label: '采样时间' }, { prop: 'healthLevel', label: '健康等级' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'serverId', label: '主机ID', type: 'number' }, { prop: 'metricCode', label: '指标编码' }, { prop: 'metricValue', label: '指标值' }, { prop: 'sampleTime', label: '采样时间' }, { prop: 'healthLevel', label: '健康等级' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
