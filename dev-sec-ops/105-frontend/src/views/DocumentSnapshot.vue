<template>
    <DataPage title="文档快照" description="文档快照维护与接口测试协作管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 'ACTIVE' }" @row-action="handleAction" />
  </template>

  <script setup>
  import { ElMessage } from 'element-plus'
  import DataPage from '../components/DataPage.vue'
  import { getDocumentSnapshotPage, addDocumentSnapshot, updateDocumentSnapshot, deleteDocumentSnapshot, publishDocumentSnapshot, archiveDocumentSnapshot } from '../api'
  const api = { page: getDocumentSnapshotPage, add: addDocumentSnapshot, update: updateDocumentSnapshot, delete: deleteDocumentSnapshot }
  const columns = [{ prop: 'projectId', label: '项目ID' }, { prop: 'snapshotNo', label: '快照编号' }, { prop: 'title', label: '文档标题' }, { prop: 'versionNo', label: '版本号' }, { prop: 'publisher', label: '发布人' }, { prop: 'status', label: '状态' }]
  const formFields = [{ prop: 'projectId', label: '项目ID', type: 'number' }, { prop: 'snapshotNo', label: '快照编号' }, { prop: 'title', label: '文档标题' }, { prop: 'versionNo', label: '版本号' }, { prop: 'contentSummary', label: '内容摘要', type: 'textarea' }, { prop: 'publisher', label: '发布人' }, { prop: 'status', label: '状态', type: 'select', options: [{ label: 'ACTIVE', value: 'ACTIVE' }, { label: 'DRAFT', value: 'DRAFT' }, { label: 'PUBLISHED', value: 'PUBLISHED' }, { label: 'RUNNING', value: 'RUNNING' }, { label: 'FINISHED', value: 'FINISHED' }] }, { prop: 'publishedAt', label: '发布时间' }]
  const rowActions = [{ label: '发布', command: 'publish', type: 'warning' }, { label: '归档', command: 'archive', type: 'warning' }]

  const handleAction = async ({ command, row, refresh }) => {
    if (command === 'publish') await publishDocumentSnapshot(row.id)
if (command === 'archive') await archiveDocumentSnapshot(row.id)
ElMessage.success('状态已更新')
    refresh()
  }

  </script>
