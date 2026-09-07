<template>
  <DataPage title="分支版本" description="分支版本数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getRepositoryBranchPage, addRepositoryBranch, updateRepositoryBranch, deleteRepositoryBranch } from '../api'
const api = { page: getRepositoryBranchPage, add: addRepositoryBranch, update: updateRepositoryBranch, delete: deleteRepositoryBranch }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'repositoryId', label: '项目ID' }, { prop: 'branchName', label: '分支名称' }, { prop: 'commitHash', label: '提交哈希' }, { prop: 'defaultFlag', label: '默认分支' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'repositoryId', label: '项目ID', type: 'number' }, { prop: 'branchName', label: '分支名称' }, { prop: 'commitHash', label: '提交哈希' }, { prop: 'defaultFlag', label: '默认分支', type: 'number' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }]
const rowActions = []
const handleAction = () => {}
</script>
