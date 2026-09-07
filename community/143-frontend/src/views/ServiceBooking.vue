<template>
  <DataPage title="服务预约" description="维护预约编号、服务项目、预约人、预约时间和人数，支撑居民发起互助预约与站点审批" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getServiceBookingPage, addServiceBooking, updateServiceBooking, deleteServiceBooking, submitServiceBooking, approveServiceBooking } from '../api'

const api = { page: getServiceBookingPage, add: addServiceBooking, update: updateServiceBooking, delete: deleteServiceBooking }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'RESIDENT'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'MANAGER'].includes(role.value))
const canDelete = computed(() => canManage.value)
const columns = [
  { prop: 'bookingNo', label: '预约编号' },
  { prop: 'projectName', label: '服务项目' },
  { prop: 'reserverName', label: '预约人' },
  { prop: 'bookingTime', label: '预约时间', width: 160 },
  { prop: 'bookingCount', label: '预约人数' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'bookingNo', label: '预约编号' },
  { prop: 'projectName', label: '服务项目' },
  { prop: 'reserverName', label: '预约人' },
  { prop: 'bookingTime', label: '预约时间' },
  { prop: 'bookingCount', label: '预约人数', type: 'number' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => {
  const actions = []
  if (canManage.value) actions.push({ command: 'submit', label: '提交', type: 'warning' })
  if (canApprove.value) actions.push({ command: 'approve', label: '审批通过', type: 'success' })
  return actions
})
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitServiceBooking(row.id)
  if (command === 'approve') await approveServiceBooking(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
