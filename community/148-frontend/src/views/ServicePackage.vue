<template>
  <DataPage
    title="服务套餐"
    description="维护套餐编号、套餐名称、服务类别、适用人群和服务周期，支撑社区养老服务标准化供给"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canManage"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getServicePackagePage, addServicePackage, updateServicePackage, deleteServicePackage, activateServicePackage, finishServicePackage } from '../api'

const api = { page: getServicePackagePage, add: addServicePackage, update: updateServicePackage, delete: deleteServicePackage }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'packageNo', label: '套餐编号' },
  { prop: 'packageName', label: '套餐名称', width: 180 },
  { prop: 'serviceCategory', label: '服务类别', width: 140 },
  { prop: 'targetGroup', label: '适用人群', width: 160 },
  { prop: 'serviceCycle', label: '服务周期', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'packageNo', label: '套餐编号' },
  { prop: 'packageName', label: '套餐名称' },
  { prop: 'serviceCategory', label: '服务类别' },
  { prop: 'targetGroup', label: '适用人群' },
  { prop: 'serviceCycle', label: '服务周期' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用中', value: 'ACTIVE' }, { label: '已归档', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '归档', type: 'warning' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateServicePackage(row.id)
  if (command === 'finish') await finishServicePackage(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
