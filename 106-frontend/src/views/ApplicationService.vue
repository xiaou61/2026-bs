<template>
  <DataPage title="应用服务" description="应用服务维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getApplicationServicePage, addApplicationService, updateApplicationService, deleteApplicationService } from '../api'
const api = { page: getApplicationServicePage, add: addApplicationService, update: updateApplicationService, delete: deleteApplicationService }
const columns = [{ prop: 'serviceName', label: '服务名称' }, { prop: 'serviceCode', label: '服务编码' }, { prop: 'language', label: '技术语言' }, { prop: 'ownerName', label: '负责人' }, { prop: 'deployType', label: '部署类型' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'serviceName', label: '服务名称' }, { prop: 'serviceCode', label: '服务编码' }, { prop: 'repoUrl', label: '仓库地址' }, { prop: 'language', label: '技术语言' }, { prop: 'ownerName', label: '负责人' }, { prop: 'deployType', label: '部署类型' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
