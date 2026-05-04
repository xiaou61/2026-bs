<template>
  <DataPage title="看板组件" description="看板组件维护与云监控告警闭环管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>
<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDashboardWidgetPage, addDashboardWidget, updateDashboardWidget, deleteDashboardWidget } from '../api'
const api = { page: getDashboardWidgetPage, add: addDashboardWidget, update: updateDashboardWidget, delete: deleteDashboardWidget }
const columns = [{ prop: 'widgetName', label: '组件名称' }, { prop: 'widgetType', label: '组件类型' }, { prop: 'metricCode', label: '指标编码' }, { prop: 'refreshSecond', label: '刷新秒数' }, { prop: 'sortNo', label: '排序' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'widgetName', label: '组件名称' }, { prop: 'widgetType', label: '组件类型' }, { prop: 'metricCode', label: '指标编码' }, { prop: 'refreshSecond', label: '刷新秒数', type: 'number' }, { prop: 'sortNo', label: '排序', type: 'number' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'ONLINE', value: 'ONLINE' }, { label: 'OPEN', value: 'OPEN' }, { label: 'ACKED', value: 'ACKED' }, { label: 'RESOLVED', value: 'RESOLVED' }, { label: 'CLOSED', value: 'CLOSED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
