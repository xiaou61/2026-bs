<template>
  <DataPage title="操作日志" description="操作日志数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" readonly @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getOperationLogPage, addOperationLog, updateOperationLog, deleteOperationLog } from '../api'
const api = { page: getOperationLogPage, add: addOperationLog, update: updateOperationLog, delete: deleteOperationLog }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'username', label: '操作人' }, { prop: 'action', label: '操作动作' }, { prop: 'targetType', label: '对象类型' }, { prop: 'targetId', label: '对象ID' }, { prop: 'ipAddress', label: 'IP地址' }]
const formFields = [{ prop: 'username', label: '操作人' }, { prop: 'action', label: '操作动作' }, { prop: 'targetType', label: '对象类型' }, { prop: 'targetId', label: '对象ID', type: 'number' }, { prop: 'detail', label: '操作详情', type: 'textarea' }, { prop: 'ipAddress', label: 'IP地址' }]
const rowActions = []
const handleAction = () => {}
</script>
