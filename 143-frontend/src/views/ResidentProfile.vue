<template>
  <DataPage title="居民档案" description="维护居民编号、姓名、所属社区、服务偏好和联系方式，支撑居民互助预约与服务对接" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getResidentProfilePage, addResidentProfile, updateResidentProfile, deleteResidentProfile, activateResidentProfile, finishResidentProfile } from '../api'

const api = { page: getResidentProfilePage, add: addResidentProfile, update: updateResidentProfile, delete: deleteResidentProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'MANAGER'].includes(role.value))
const columns = [
  { prop: 'residentNo', label: '居民编号' },
  { prop: 'residentName', label: '居民姓名' },
  { prop: 'communityName', label: '所属社区' },
  { prop: 'servicePreference', label: '服务偏好' },
  { prop: 'phone', label: '联系电话', width: 150 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'residentNo', label: '居民编号' },
  { prop: 'residentName', label: '居民姓名' },
  { prop: 'communityName', label: '所属社区' },
  { prop: 'servicePreference', label: '服务偏好' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateResidentProfile(row.id)
  if (command === 'finish') await finishResidentProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
