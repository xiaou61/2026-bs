<template>
  <DataPage
    title="投诉工单"
    description="维护工单编号、投诉主题、噪声类型、投诉区域和发生时间，支撑扰民投诉受理与办结跟踪"
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
import { getComplaintTicketPage, addComplaintTicket, updateComplaintTicket, deleteComplaintTicket, activateComplaintTicket, finishComplaintTicket } from '../api'

const api = { page: getComplaintTicketPage, add: addComplaintTicket, update: updateComplaintTicket, delete: deleteComplaintTicket }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CITIZEN'].includes(role.value))
const canProcess = computed(() => ['ADMIN', 'OFFICER'].includes(role.value))
const columns = [
  { prop: 'ticketNo', label: '工单编号' },
  { prop: 'complaintTitle', label: '投诉标题', width: 180 },
  { prop: 'noiseType', label: '噪声类型', width: 140 },
  { prop: 'complaintArea', label: '投诉区域', width: 180 },
  { prop: 'complaintTime', label: '投诉时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'ticketNo', label: '工单编号' },
  { prop: 'complaintTitle', label: '投诉标题' },
  { prop: 'noiseType', label: '噪声类型' },
  { prop: 'complaintArea', label: '投诉区域' },
  { prop: 'complaintTime', label: '投诉时间' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '待处理', value: 'OPEN' },
      { label: '受理中', value: 'ACTIVE' },
      { label: '已办结', value: 'FINISHED' }
    ]
  }
]
const rowActions = computed(() => {
  if (!canProcess.value) return []
  return [
    { command: 'activate', label: '受理', type: 'success' },
    { command: 'finish', label: '办结', type: 'primary' }
  ]
})
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateComplaintTicket(row.id)
  if (command === 'finish') await finishComplaintTicket(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
