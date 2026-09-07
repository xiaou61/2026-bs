<template>
  <DataPage
    title="现场巡查"
    description="维护巡查编号、巡查人员、巡查时间和结论记录，支撑现场核查和执法取证闭环"
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
import { getPatrolRecordPage, addPatrolRecord, updatePatrolRecord, deletePatrolRecord, submitPatrolRecord, approvePatrolRecord } from '../api'

const api = { page: getPatrolRecordPage, add: addPatrolRecord, update: updatePatrolRecord, delete: deletePatrolRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'OFFICER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'SUPERVISOR'].includes(role.value))
const columns = [
  { prop: 'patrolNo', label: '巡查编号' },
  { prop: 'complaintTitle', label: '投诉标题', width: 180 },
  { prop: 'patrolOfficer', label: '巡查人', width: 140 },
  { prop: 'patrolTime', label: '巡查时间', width: 160 },
  { prop: 'patrolResult', label: '巡查结论', width: 220 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'patrolNo', label: '巡查编号' },
  { prop: 'complaintTitle', label: '投诉标题' },
  { prop: 'patrolOfficer', label: '巡查人' },
  { prop: 'patrolTime', label: '巡查时间' },
  { prop: 'patrolResult', label: '巡查结论', type: 'textarea' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '待处理', value: 'OPEN' },
      { label: '已提交', value: 'SUBMITTED' },
      { label: '已审批', value: 'APPROVED' }
    ]
  }
]
const rowActions = computed(() => {
  const actions = []
  if (canManage.value) actions.push({ command: 'submit', label: '提交', type: 'primary' })
  if (canApprove.value) actions.push({ command: 'approve', label: '审批', type: 'success' })
  return actions
})
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitPatrolRecord(row.id)
  if (command === 'approve') await approvePatrolRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
