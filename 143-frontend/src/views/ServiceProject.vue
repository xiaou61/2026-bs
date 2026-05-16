<template>
  <DataPage title="服务项目" description="维护项目编号、项目名称、服务主题、服务对象和发布时间，支撑社区公益服务项目发布与状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getServiceProjectPage, addServiceProject, updateServiceProject, deleteServiceProject, activateServiceProject, finishServiceProject } from '../api'

const api = { page: getServiceProjectPage, add: addServiceProject, update: updateServiceProject, delete: deleteServiceProject }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'MANAGER'].includes(role.value))
const columns = [
  { prop: 'projectNo', label: '项目编号' },
  { prop: 'projectName', label: '项目名称' },
  { prop: 'serviceTopic', label: '服务主题' },
  { prop: 'serviceTarget', label: '服务对象' },
  { prop: 'publishTime', label: '发布时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'projectNo', label: '项目编号' },
  { prop: 'projectName', label: '项目名称' },
  { prop: 'serviceTopic', label: '服务主题' },
  { prop: 'serviceTarget', label: '服务对象' },
  { prop: 'publishTime', label: '发布时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateServiceProject(row.id)
  if (command === 'finish') await finishServiceProject(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
