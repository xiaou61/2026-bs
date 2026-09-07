<template>
  <DataPage title="响应字段" description="响应字段维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getResponseFieldPage, addResponseField, updateResponseField, deleteResponseField } from '../api'
const api = { page: getResponseFieldPage, add: addResponseField, update: updateResponseField, delete: deleteResponseField }
const columns = [{ prop: 'endpointId', label: '接口ID' }, { prop: 'fieldName', label: '字段名' }, { prop: 'dataType', label: '数据类型' }, { prop: 'parentPath', label: '父级路径' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'endpointId', label: '接口ID', type: 'number' }, { prop: 'fieldName', label: '字段名' }, { prop: 'dataType', label: '数据类型' }, { prop: 'parentPath', label: '父级路径' }, { prop: 'exampleValue', label: '示例值' }, { prop: 'description', label: '说明', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
