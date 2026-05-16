<template>
  <DataPage title="路线方案" description="维护方案编号、志愿者编号、路线类型、路线详情和生成时间，支撑调度方案制定与确认" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getRoutePlanPage, addRoutePlan, updateRoutePlan, deleteRoutePlan, submitRoutePlan, approveRoutePlan } from '../api'

const api = { page: getRoutePlanPage, add: addRoutePlan, update: updateRoutePlan, delete: deleteRoutePlan }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'DISPATCHER'].includes(role.value))
const columns = [
  { prop: 'planNo', label: '方案编号' },
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'routeType', label: '路线类型' },
  { prop: 'routeDetail', label: '路线详情', width: 220 },
  { prop: 'generateTime', label: '生成时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'planNo', label: '方案编号' },
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'routeType', label: '路线类型' },
  { prop: 'routeDetail', label: '路线详情', type: 'textarea' },
  { prop: 'generateTime', label: '生成时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'submit', label: '提交', type: 'primary' },
  { command: 'approve', label: '通过', type: 'success' }
] : [])
const defaults = { status: 'SUBMITTED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitRoutePlan(row.id)
  if (command === 'approve') await approveRoutePlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
