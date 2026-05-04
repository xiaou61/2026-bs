<template>
  <DataPage title="发布环境" description="发布环境维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDevopsEnvironmentPage, addDevopsEnvironment, updateDevopsEnvironment, deleteDevopsEnvironment } from '../api'
const api = { page: getDevopsEnvironmentPage, add: addDevopsEnvironment, update: updateDevopsEnvironment, delete: deleteDevopsEnvironment }
const columns = [{ prop: 'envName', label: '环境名称' }, { prop: 'envCode', label: '环境编码' }, { prop: 'clusterName', label: '集群名称' }, { prop: 'namespaceName', label: '命名空间' }, { prop: 'ownerName', label: '负责人' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'envName', label: '环境名称' }, { prop: 'envCode', label: '环境编码' }, { prop: 'clusterName', label: '集群名称' }, { prop: 'namespaceName', label: '命名空间' }, { prop: 'ownerName', label: '负责人' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
