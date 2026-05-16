<template>
  <DataPage title="互助兑换" description="维护兑换编号、关联项目、兑换人、兑换时长和兑换时间，支撑互助时长兑换申请与审批" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getTimeExchangePage, addTimeExchange, updateTimeExchange, deleteTimeExchange, submitTimeExchange, approveTimeExchange } from '../api'

const api = { page: getTimeExchangePage, add: addTimeExchange, update: updateTimeExchange, delete: deleteTimeExchange }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'RESIDENT', 'VOLUNTEER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'MANAGER'].includes(role.value))
const columns = [
  { prop: 'exchangeNo', label: '兑换编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'exchangerName', label: '兑换人' },
  { prop: 'exchangeHours', label: '兑换时长(小时)' },
  { prop: 'exchangeTime', label: '兑换时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'exchangeNo', label: '兑换编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'exchangerName', label: '兑换人' },
  { prop: 'exchangeHours', label: '兑换时长(小时)', type: 'number' },
  { prop: 'exchangeTime', label: '兑换时间' },
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
  if (command === 'submit') await submitTimeExchange(row.id)
  if (command === 'approve') await approveTimeExchange(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
