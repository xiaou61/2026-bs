<template>
  <DataPage title="项目台账" description="项目台账数据维护与合规流程管理" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getProjectRepositoryPage, addProjectRepository, updateProjectRepository, deleteProjectRepository, archiveProjectRepository, activateProjectRepository } from '../api'
const api = { page: getProjectRepositoryPage, add: addProjectRepository, update: updateProjectRepository, delete: deleteProjectRepository }
const filters = [{ prop: 'keyword', label: '关键词' }]
const columns = [{ prop: 'repoName', label: '项目名称' }, { prop: 'repoCode', label: '项目编码' }, { prop: 'ownerTeam', label: '负责团队' }, { prop: 'language', label: '技术语言' }, { prop: 'riskLevel', label: '风险等级' }, { prop: 'status', label: '状态' }]
const formFields = [{ prop: 'repoName', label: '项目名称' }, { prop: 'repoCode', label: '项目编码' }, { prop: 'ownerTeam', label: '负责团队' }, { prop: 'gitUrl', label: '仓库地址' }, { prop: 'language', label: '技术语言' }, { prop: 'riskLevel', label: '风险等级' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }, { label: 'OPEN', value: 'OPEN' }] }, { prop: 'lastScanTime', label: '最近扫描' }]
const rowActions = [{ label: '归档', command: 'archive', type: 'warning' }, { label: '启用', command: 'activate', type: 'warning' }]

const handleAction = async ({ command, row, refresh }) => {
    if (command === 'archive') await archiveProjectRepository(row.id)
if (command === 'activate') await activateProjectRepository(row.id)
ElMessage.success('状态已更新')
  refresh()
}

</script>
