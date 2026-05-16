<template>
  <DataPage
    title="处置任务"
    description="维护任务编号、投诉标题、责任人、下发时间和处理时限，支撑执法处置流转与审批"
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
import { getHandlingTaskPage, addHandlingTask, updateHandlingTask, deleteHandlingTask, submitHandlingTask, approveHandlingTask } from '../api'

const api = { page: getHandlingTaskPage, add: addHandlingTask, update: updateHandlingTask, delete: deleteHandlingTask }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'SUPERVISOR'].includes(role.value))
const canSubmit = computed(() => ['ADMIN', 'OFFICER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'SUPERVISOR'].includes(role.value))
const columns = [
  { prop: 'taskNo', label: '任务编号' },
  { prop: 'complaintTitle', label: '投诉标题', width: 180 },
  { prop: 'assigneeName', label: '责任人', width: 140 },
  { prop: 'assignTime', label: '下发时间', width: 160 },
  { prop: 'deadlineTime', label: '处理时限', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'taskNo', label: '任务编号' },
  { prop: 'complaintTitle', label: '投诉标题' },
  { prop: 'assigneeName', label: '责任人' },
  { prop: 'assignTime', label: '下发时间' },
  { prop: 'deadlineTime', label: '处理时限' },
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
  if (canSubmit.value) actions.push({ command: 'submit', label: '提交', type: 'primary' })
  if (canApprove.value) actions.push({ command: 'approve', label: '审批', type: 'success' })
  return actions
})
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitHandlingTask(row.id)
  if (command === 'approve') await approveHandlingTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
