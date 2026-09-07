<template>
  <DataPage title="服务分类" description="维护分类编号、分类名称、服务类型、目标群体和建议时长，支撑时间银行服务分类管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getServiceCategoryPage, addServiceCategory, updateServiceCategory, deleteServiceCategory, activateServiceCategory, finishServiceCategory } from '../api'

const api = { page: getServiceCategoryPage, add: addServiceCategory, update: updateServiceCategory, delete: deleteServiceCategory }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'MANAGER'].includes(role.value))
const columns = [
  { prop: 'categoryNo', label: '分类编号' },
  { prop: 'categoryName', label: '分类名称' },
  { prop: 'serviceType', label: '服务类型' },
  { prop: 'targetGroup', label: '目标群体' },
  { prop: 'suggestedDuration', label: '建议时长(分钟)' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'categoryNo', label: '分类编号' },
  { prop: 'categoryName', label: '分类名称' },
  { prop: 'serviceType', label: '服务类型' },
  { prop: 'targetGroup', label: '目标群体' },
  { prop: 'suggestedDuration', label: '建议时长(分钟)', type: 'number' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateServiceCategory(row.id)
  if (command === 'finish') await finishServiceCategory(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
