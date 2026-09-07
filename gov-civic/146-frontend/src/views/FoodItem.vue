<template>
  <DataPage
    title="食品档案"
    description="维护抽检食品批次、品类和当前抽检状态"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canDelete"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { activateFoodItem, addFoodItem, deleteFoodItem, finishFoodItem, getFoodItemPage, updateFoodItem } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getFoodItemPage, add: addFoodItem, update: updateFoodItem, delete: deleteFoodItem }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canManage = computed(() => ['ADMIN', 'INSPECTOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'INSPECTOR'].includes(role.value)) actions.push({ command: 'activate', label: '纳入抽检', type: 'success' })
  if (['ADMIN', 'REVIEWER'].includes(role.value)) actions.push({ command: 'finish', label: '完成跟踪', type: 'warning' })
  return actions
})
const statusOptions = [
  { label: '待排期', value: 'PENDING' },
  { label: '已启用', value: 'ACTIVE' },
  { label: '处理中', value: 'PROCESSING' },
  { label: '已完成', value: 'FINISHED' }
]
const inspectionStatusOptions = [
  { label: '待抽样', value: '待抽样' },
  { label: '检测中', value: '检测中' },
  { label: '已公示', value: '已公示' }
]
const columns = [
  { prop: 'foodNo', label: '食品编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'foodType', label: '食品类型' },
  { prop: 'batchNo', label: '生产批次' },
  { prop: 'inspectionStatus', label: '抽检状态' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'foodNo', label: '食品编号' },
  { prop: 'foodName', label: '食品名称' },
  { prop: 'foodType', label: '食品类型' },
  { prop: 'batchNo', label: '生产批次' },
  { prop: 'inspectionStatus', label: '抽检状态', type: 'select', options: inspectionStatusOptions },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { inspectionStatus: '待抽样', status: 'PENDING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateFoodItem(row.id)
  if (command === 'finish') await finishFoodItem(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
