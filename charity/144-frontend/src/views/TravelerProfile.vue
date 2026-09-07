<template>
  <DataPage title="出行用户" description="维护用户编号、姓名、联系电话、协助需求和出行偏好，支撑服务预约与行程协同" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getTravelerProfilePage, addTravelerProfile, updateTravelerProfile, deleteTravelerProfile, processTravelerProfile, finishTravelerProfile } from '../api'

const api = { page: getTravelerProfilePage, add: addTravelerProfile, update: updateTravelerProfile, delete: deleteTravelerProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAVELER', 'DISPATCHER'].includes(role.value))
const canDispatch = computed(() => ['ADMIN', 'DISPATCHER'].includes(role.value))
const columns = [
  { prop: 'travelerNo', label: '用户编号' },
  { prop: 'travelerName', label: '用户姓名' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'assistanceNeed', label: '协助需求', width: 180 },
  { prop: 'travelPreference', label: '出行偏好', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'travelerNo', label: '用户编号' },
  { prop: 'travelerName', label: '用户姓名' },
  { prop: 'phone', label: '联系电话' },
  { prop: 'assistanceNeed', label: '协助需求' },
  { prop: 'travelPreference', label: '出行偏好' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canDispatch.value ? [
  { command: 'process', label: '处理', type: 'warning' },
  { command: 'finish', label: '完成', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processTravelerProfile(row.id)
  if (command === 'finish') await finishTravelerProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
