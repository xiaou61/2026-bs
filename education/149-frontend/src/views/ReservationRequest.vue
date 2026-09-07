<template>
  <DataPage title="预约申请" description="预约编号、设备名称、申请人、预约时间、预约状态和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getReservationRequestPage, addReservationRequest, updateReservationRequest, deleteReservationRequest, submitReservationRequest, approveReservationRequest } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const api = { page: getReservationRequestPage, add: addReservationRequest, update: updateReservationRequest, delete: deleteReservationRequest }
const canApprove = computed(() => ['ADMIN', 'TEACHER', 'MANAGER'].includes(userStore.user?.role))
const columns = [
  { prop: 'reservationNo', label: '预约编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'applicantName', label: '申请人' },
  { prop: 'reservationTime', label: '预约时间' },
  { prop: 'reservationStatus', label: '预约状态' },
  { prop: 'status', label: '流转状态' }
]
const formFields = [
  { prop: 'reservationNo', label: '预约编号' },
  { prop: 'equipmentName', label: '设备名称' },
  { prop: 'applicantName', label: '申请人' },
  { prop: 'reservationTime', label: '预约时间' },
  { prop: 'reservationStatus', label: '预约状态' },
  {
    prop: 'status',
    label: '流转状态',
    type: 'select',
    options: [
      { label: '待提交', value: 'OPEN' },
      { label: '已提交', value: 'SUBMITTED' },
      { label: '已通过', value: 'APPROVED' }
    ]
  }
]
const rowActions = computed(() => {
  const actions = [{ command: 'submit', label: '提交', type: 'primary' }]
  if (canApprove.value) actions.push({ command: 'approve', label: '通过', type: 'success' })
  return actions
})
const defaults = { reservationStatus: '待教师审核', status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitReservationRequest(row.id)
  if (command === 'approve') await approveReservationRequest(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
