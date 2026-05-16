<template>
  <DataPage title="协助预约" description="维护预约编号、预约标题、出发点、预约时间和目的地，支撑出行协助申请与审批" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getAssistRequestPage, addAssistRequest, updateAssistRequest, deleteAssistRequest, submitAssistRequest, approveAssistRequest } from '../api'

const api = { page: getAssistRequestPage, add: addAssistRequest, update: updateAssistRequest, delete: deleteAssistRequest }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAVELER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'DISPATCHER'].includes(role.value))
const columns = [
  { prop: 'requestNo', label: '预约编号' },
  { prop: 'requestTitle', label: '预约标题' },
  { prop: 'departurePoint', label: '出发点', width: 180 },
  { prop: 'requestTime', label: '预约时间', width: 160 },
  { prop: 'destination', label: '目的地', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'requestNo', label: '预约编号' },
  { prop: 'requestTitle', label: '预约标题' },
  { prop: 'departurePoint', label: '出发点' },
  { prop: 'requestTime', label: '预约时间' },
  { prop: 'destination', label: '目的地' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => {
  const actions = []
  if (canManage.value) actions.push({ command: 'submit', label: '提交', type: 'primary' })
  if (canApprove.value) actions.push({ command: 'approve', label: '通过', type: 'success' })
  return actions
})
const defaults = { status: 'SUBMITTED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitAssistRequest(row.id)
  if (command === 'approve') await approveAssistRequest(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
