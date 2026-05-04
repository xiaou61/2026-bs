<template>
  <DataPage title="请求参数" description="请求参数维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRequestParamPage, addRequestParam, updateRequestParam, deleteRequestParam } from '../api'
const api = { page: getRequestParamPage, add: addRequestParam, update: updateRequestParam, delete: deleteRequestParam }
const columns = [{ prop: 'endpointId', label: '接口ID' }, { prop: 'paramName', label: '参数名' }, { prop: 'paramType', label: '参数位置' }, { prop: 'dataType', label: '数据类型' }, { prop: 'requiredFlag', label: '必填' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'endpointId', label: '接口ID', type: 'number' }, { prop: 'paramName', label: '参数名' }, { prop: 'paramType', label: '参数位置' }, { prop: 'dataType', label: '数据类型' }, { prop: 'requiredFlag', label: '必填', type: 'number' }, { prop: 'exampleValue', label: '示例值' }, { prop: 'description', label: '说明', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
