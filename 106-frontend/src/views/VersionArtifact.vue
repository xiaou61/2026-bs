<template>
  <DataPage title="版本制品" description="版本制品维护与DevOps发布流程管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getVersionArtifactPage, addVersionArtifact, updateVersionArtifact, deleteVersionArtifact } from '../api'
const api = { page: getVersionArtifactPage, add: addVersionArtifact, update: updateVersionArtifact, delete: deleteVersionArtifact }
const columns = [{ prop: 'serviceId', label: '服务ID' }, { prop: 'versionNo', label: '版本号' }, { prop: 'artifactName', label: '制品名称' }, { prop: 'imageTag', label: '镜像标签' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'serviceId', label: '服务ID', type: 'number' }, { prop: 'versionNo', label: '版本号' }, { prop: 'artifactName', label: '制品名称' }, { prop: 'imageTag', label: '镜像标签' }, { prop: 'checksum', label: '校验值' }, { prop: 'buildTime', label: '构建时间' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'SUBMITTED', value: 'SUBMITTED' }, { label: 'APPROVED', value: 'APPROVED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
const rowActions = []
const handleAction = () => {}
</script>
