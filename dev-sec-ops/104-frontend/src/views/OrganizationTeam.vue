<template>
  <DataPage title="组织团队" description="组织团队数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getOrganizationTeamPage, addOrganizationTeam, updateOrganizationTeam, deleteOrganizationTeam } from '../api'
const api = { page: getOrganizationTeamPage, add: addOrganizationTeam, update: updateOrganizationTeam, delete: deleteOrganizationTeam }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'teamName', label: '团队名称' }, { prop: 'leaderName', label: '负责人' }, { prop: 'contactPhone', label: '联系电话' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'teamName', label: '团队名称' }, { prop: 'leaderName', label: '负责人' }, { prop: 'contactPhone', label: '联系电话' }, { prop: 'description', label: '团队说明', type: 'textarea' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }]
const rowActions = []
const handleAction = () => {}
</script>
