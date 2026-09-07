<template>
  <DataPage title="依赖台账" description="依赖台账数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getDependencyPackagePage, addDependencyPackage, updateDependencyPackage, deleteDependencyPackage } from '../api'
const api = { page: getDependencyPackagePage, add: addDependencyPackage, update: updateDependencyPackage, delete: deleteDependencyPackage }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'repositoryId', label: '项目ID' }, { prop: 'packageName', label: '依赖名称' }, { prop: 'packageVersion', label: '版本号' }, { prop: 'packageManager', label: '包管理器' }, { prop: 'licenseName', label: '许可证' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'repositoryId', label: '项目ID', type: 'number' }, { prop: 'packageName', label: '依赖名称' }, { prop: 'packageVersion', label: '版本号' }, { prop: 'packageManager', label: '包管理器' }, { prop: 'licenseName', label: '许可证' }, { prop: 'scopeName', label: '依赖范围' }, { prop: 'sourceUrl', label: '来源地址' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }]
const rowActions = []
const handleAction = () => {}
</script>
