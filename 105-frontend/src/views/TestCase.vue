<template>
    <DataPage title="测试用例" description="测试用例维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getTestCasePage, addTestCase, updateTestCase, deleteTestCase, enableTestCase, disableTestCase } from '../api'
  const api = { page: getTestCasePage, add: addTestCase, update: updateTestCase, delete: deleteTestCase }
  const columns = [{ prop: 'endpointId', label: '接口ID' }, { prop: 'caseName', label: '用例名称' }, { prop: 'caseType', label: '用例类型' }, { prop: 'priority', label: '优先级' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'endpointId', label: '接口ID', type: 'number' }, { prop: 'caseName', label: '用例名称' }, { prop: 'caseType', label: '用例类型' }, { prop: 'priority', label: '优先级' }, { prop: 'requestBody', label: '请求体', type: 'textarea' }, { prop: 'expectedResult', label: '预期结果', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
  const rowActions = [{ label: '启用', command: 'enable', type: 'warning' }, { label: '停用', command: 'disable', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'enable') await enableTestCase(row.id)
if (command === 'disable') await disableTestCase(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
