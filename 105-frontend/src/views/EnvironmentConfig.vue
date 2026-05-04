<template>
  <DataPage title="环境配置" description="环境配置维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getEnvironmentConfigPage, addEnvironmentConfig, updateEnvironmentConfig, deleteEnvironmentConfig } from '../api'
const api = { page: getEnvironmentConfigPage, add: addEnvironmentConfig, update: updateEnvironmentConfig, delete: deleteEnvironmentConfig }
const columns = [{ prop: 'projectId', label: '项目ID' }, { prop: 'envName', label: '环境名称' }, { prop: 'baseUrl', label: '基础地址' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'projectId', label: '项目ID', type: 'number' }, { prop: 'envName', label: '环境名称' }, { prop: 'baseUrl', label: '基础地址' }, { prop: 'headers', label: '请求头', type: 'textarea' }, { prop: 'variables', label: '变量', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
