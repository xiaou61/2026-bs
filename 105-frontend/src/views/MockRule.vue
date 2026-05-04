<template>
  <DataPage title="Mock规则" description="Mock规则维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMockRulePage, addMockRule, updateMockRule, deleteMockRule } from '../api'
const api = { page: getMockRulePage, add: addMockRule, update: updateMockRule, delete: deleteMockRule }
const columns = [{ prop: 'endpointId', label: '接口ID' }, { prop: 'ruleName', label: '规则名称' }, { prop: 'responseCode', label: '响应码' }, { prop: 'delayMs', label: '延迟毫秒' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'endpointId', label: '接口ID', type: 'number' }, { prop: 'ruleName', label: '规则名称' }, { prop: 'matchExpression', label: '匹配表达式' }, { prop: 'responseCode', label: '响应码', type: 'number' }, { prop: 'responseBody', label: '响应内容', type: 'textarea' }, { prop: 'delayMs', label: '延迟毫秒', type: 'number' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
