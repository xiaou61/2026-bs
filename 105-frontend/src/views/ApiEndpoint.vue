<template>
    <DataPage title="接口定义" description="接口定义维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getApiEndpointPage, addApiEndpoint, updateApiEndpoint, deleteApiEndpoint, publishApiEndpoint, offlineApiEndpoint } from '../api'
  const api = { page: getApiEndpointPage, add: addApiEndpoint, update: updateApiEndpoint, delete: deleteApiEndpoint }
  const columns = [{ prop: 'projectId', label: '项目ID' }, { prop: 'apiName', label: '接口名称' }, { prop: 'method', label: '请求方式' }, { prop: 'pathUrl', label: '接口路径' }, { prop: 'ownerName', label: '维护人' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'projectId', label: '项目ID', type: 'number' }, { prop: 'groupId', label: '分组ID', type: 'number' }, { prop: 'apiName', label: '接口名称' }, { prop: 'method', label: '请求方式', type: 'select', options: [{ label: 'GET', value: 'GET' }, { label: 'POST', value: 'POST' }, { label: 'PUT', value: 'PUT' }, { label: 'DELETE', value: 'DELETE' }] }, { prop: 'pathUrl', label: '接口路径' }, { prop: 'contentType', label: '内容类型' }, { prop: 'ownerName', label: '维护人' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }, { prop: 'description', label: '说明', type: 'textarea' }]
  const rowActions = [{ label: '发布', command: 'publish', type: 'warning' }, { label: '下线', command: 'offline', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'publish') await publishApiEndpoint(row.id)
if (command === 'offline') await offlineApiEndpoint(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
