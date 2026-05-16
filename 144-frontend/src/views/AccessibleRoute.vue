<template>
  <DataPage title="无障碍路线" description="维护路线编号、路线名称、路线类型、适用场景和建议时长，支撑出行路径规划与发布" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getAccessibleRoutePage, addAccessibleRoute, updateAccessibleRoute, deleteAccessibleRoute, activateAccessibleRoute, finishAccessibleRoute } from '../api'

const api = { page: getAccessibleRoutePage, add: addAccessibleRoute, update: updateAccessibleRoute, delete: deleteAccessibleRoute }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'DISPATCHER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'routeNo', label: '路线编号' },
  { prop: 'routeName', label: '路线名称' },
  { prop: 'routeType', label: '路线类型' },
  { prop: 'travelScenario', label: '适用场景', width: 180 },
  { prop: 'suggestedDuration', label: '建议时长(分钟)', width: 150 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'routeNo', label: '路线编号' },
  { prop: 'routeName', label: '路线名称' },
  { prop: 'routeType', label: '路线类型' },
  { prop: 'travelScenario', label: '适用场景' },
  { prop: 'suggestedDuration', label: '建议时长(分钟)', type: 'number' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '完成', type: 'primary' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateAccessibleRoute(row.id)
  if (command === 'finish') await finishAccessibleRoute(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
