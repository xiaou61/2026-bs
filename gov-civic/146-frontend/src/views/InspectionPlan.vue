<template>
  <DataPage
    title="抽检计划"
    description="维护食品安全抽检计划、区域范围和执行周期"
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
import { activateInspectionPlan, addInspectionPlan, deleteInspectionPlan, finishInspectionPlan, getInspectionPlanPage, updateInspectionPlan } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getInspectionPlanPage, add: addInspectionPlan, update: updateInspectionPlan, delete: deleteInspectionPlan }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canManage = computed(() => ['ADMIN', 'INSPECTOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'INSPECTOR'].includes(role.value)) actions.push({ command: 'activate', label: '发布计划', type: 'success' })
  if (['ADMIN', 'REVIEWER'].includes(role.value)) actions.push({ command: 'finish', label: '完结', type: 'warning' })
  return actions
})
const statusOptions = [
  { label: '待排期', value: 'PENDING' },
  { label: '已发布', value: 'ACTIVE' },
  { label: '已完成', value: 'FINISHED' }
]
const columns = [
  { prop: 'planNo', label: '计划编号' },
  { prop: 'planName', label: '计划名称' },
  { prop: 'inspectionType', label: '抽检类型' },
  { prop: 'regionName', label: '负责区域' },
  { prop: 'inspectionCycle', label: '计划周期' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'planNo', label: '计划编号' },
  { prop: 'planName', label: '计划名称' },
  { prop: 'inspectionType', label: '抽检类型' },
  { prop: 'regionName', label: '负责区域' },
  { prop: 'inspectionCycle', label: '计划周期' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { status: 'PENDING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateInspectionPlan(row.id)
  if (command === 'finish') await finishInspectionPlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
