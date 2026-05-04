<template>
    <DataPage title="接口项目" description="接口项目维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getApiProjectPage, addApiProject, updateApiProject, deleteApiProject, archiveApiProject, activateApiProject } from '../api'
  const api = { page: getApiProjectPage, add: addApiProject, update: updateApiProject, delete: deleteApiProject }
  const columns = [{ prop: 'projectName', label: '项目名称' }, { prop: 'projectCode', label: '项目编码' }, { prop: 'ownerName', label: '负责人' }, { prop: 'baseUrl', label: '基础地址' }, { prop: 'versionNo', label: '版本号' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'projectName', label: '项目名称' }, { prop: 'projectCode', label: '项目编码' }, { prop: 'ownerName', label: '负责人' }, { prop: 'baseUrl', label: '基础地址' }, { prop: 'authType', label: '鉴权类型' }, { prop: 'versionNo', label: '版本号' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }]
  const rowActions = [{ label: '归档', command: 'archive', type: 'warning' }, { label: '启用', command: 'activate', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'archive') await archiveApiProject(row.id)
if (command === 'activate') await activateApiProject(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
