<template>
  <DataPage title="接口分组" description="接口分组维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getApiGroupPage, addApiGroup, updateApiGroup, deleteApiGroup } from '../api'
const api = { page: getApiGroupPage, add: addApiGroup, update: updateApiGroup, delete: deleteApiGroup }
const columns = [{ prop: 'projectId', label: '项目ID' }, { prop: 'groupName', label: '分组名称' }, { prop: 'parentName', label: '父级分组' }, { prop: 'sortNo', label: '排序' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'projectId', label: '项目ID', type: 'number' }, { prop: 'groupName', label: '分组名称' }, { prop: 'parentName', label: '父级分组' }, { prop: 'sortNo', label: '排序', type: 'number' }, { prop: 'description', label: '说明', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
