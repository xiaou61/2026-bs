<template>
  <DataPage title="流水线" description="流水线维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDeployPipelinePage, addDeployPipeline, updateDeployPipeline, deleteDeployPipeline } from '../api'
const api = { page: getDeployPipelinePage, add: addDeployPipeline, update: updateDeployPipeline, delete: deleteDeployPipeline }
const columns = [{ prop: 'serviceId', label: '服务ID' }, { prop: 'pipelineName', label: '流水线名称' }, { prop: 'branchName', label: '默认分支' }, { prop: 'imageRepo', label: '镜像仓库' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'serviceId', label: '服务ID', type: 'number' }, { prop: 'pipelineName', label: '流水线名称' }, { prop: 'branchName', label: '默认分支' }, { prop: 'buildCommand', label: '构建命令' }, { prop: 'imageRepo', label: '镜像仓库' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
