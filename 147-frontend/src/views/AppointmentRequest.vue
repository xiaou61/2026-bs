<template>
  <DataPage
    title="预约申请"
    description="维护预约编号、咨询主题、申请人、预约时间和预约状态，支撑学生来访申请与审核分配"
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
import { getAppointmentRequestPage, addAppointmentRequest, updateAppointmentRequest, deleteAppointmentRequest, submitAppointmentRequest, approveAppointmentRequest } from '../api'

const api = { page: getAppointmentRequestPage, add: addAppointmentRequest, update: updateAppointmentRequest, delete: deleteAppointmentRequest }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'STUDENT'].includes(role.value))
const canSubmit = computed(() => ['ADMIN', 'STUDENT'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'TEACHER', 'COUNSELOR'].includes(role.value))
const columns = [
  { prop: 'appointmentNo', label: '预约编号' },
  { prop: 'caseTheme', label: '咨询主题', width: 180 },
  { prop: 'applicantName', label: '申请人', width: 140 },
  { prop: 'appointmentTime', label: '预约时间', width: 160 },
  { prop: 'appointmentStatus', label: '预约状态', width: 160 },
  { prop: 'status', label: '流程状态' }
]
const formFields = [
  { prop: 'appointmentNo', label: '预约编号' },
  { prop: 'caseTheme', label: '咨询主题' },
  { prop: 'applicantName', label: '申请人' },
  { prop: 'appointmentTime', label: '预约时间' },
  { prop: 'appointmentStatus', label: '预约状态' },
  { prop: 'status', label: '流程状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '已提交', value: 'SUBMITTED' }, { label: '已通过', value: 'APPROVED' }] }
]
const rowActions = computed(() => {
  const actions = []
  if (canSubmit.value) {
    actions.push({ command: 'submit', label: '提交', type: 'primary' })
  }
  if (canApprove.value) {
    actions.push({ command: 'approve', label: '通过', type: 'success' })
  }
  return actions
})
const defaults = { appointmentStatus: '待审核', status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitAppointmentRequest(row.id)
  if (command === 'approve') await approveAppointmentRequest(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>








