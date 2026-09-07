<template>
  <DataPage title="检查预约" description="预约编号、检查项目、预约日期、检查时段、开单医生和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canEditBase" :can-edit="canEditBase" :can-delete="canEditBase" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addExamAppointment, approveExamAppointment, deleteExamAppointment, getExamAppointmentPage, submitExamAppointment, updateExamAppointment } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canEditBase = computed(() => ['ADMIN', 'DOCTOR', 'PATIENT'].includes(userStore.user?.role))
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'DOCTOR', 'PATIENT'].includes(userStore.user?.role)) {
    actions.push({ command: 'submit', label: '提交预约', type: 'primary' })
  }
  if (['ADMIN', 'DOCTOR', 'TECHNICIAN'].includes(userStore.user?.role)) {
    actions.push({ command: 'approve', label: '审核通过', type: 'success' })
  }
  return actions
})
const api = { page: getExamAppointmentPage, add: addExamAppointment, update: updateExamAppointment, delete: deleteExamAppointment }
const columns = [
  { prop: 'appointmentNo', label: '预约编号' },
  { prop: 'itemName', label: '检查项目' },
  { prop: 'appointmentDate', label: '预约日期' },
  { prop: 'timeSlot', label: '检查时段' },
  { prop: 'doctorName', label: '开单医生' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'appointmentNo', label: '预约编号' },
  { prop: 'itemName', label: '检查项目' },
  { prop: 'appointmentDate', label: '预约日期' },
  { prop: 'timeSlot', label: '检查时段' },
  { prop: 'doctorName', label: '开单医生' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '待处理', value: 'OPEN' },
      { label: '已提交', value: 'SUBMITTED' },
      { label: '已通过', value: 'APPROVED' }
    ]
  }
]
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitExamAppointment(row.id)
  if (command === 'approve') await approveExamAppointment(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
