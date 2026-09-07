<template>
  <DataPage title="盘点任务" description="维护盘点任务编号、盘点资产、计划数量和执行人，支撑 RFID 盘点排期与下发" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getInventoryTaskPage, addInventoryTask, updateInventoryTask, deleteInventoryTask, submitInventoryTask, approveInventoryTask } from '../api'

const api = { page: getInventoryTaskPage, add: addInventoryTask, update: updateInventoryTask, delete: deleteInventoryTask }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'ASSET_ADMIN'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'AUDITOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'taskNo', label: '任务编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'plannedCount', label: '计划数量' },
  { prop: 'executorName', label: '执行人' },
  { prop: 'taskTime', label: '任务时间', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'taskNo', label: '任务编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'plannedCount', label: '计划数量', type: 'number' },
  { prop: 'executorName', label: '执行人' },
  { prop: 'taskTime', label: '任务时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => {
  const actions = []
  if (canManage.value) {
    actions.push({ command: 'submit', label: '提交', type: 'warning' })
  }
  if (canApprove.value) {
    actions.push({ command: 'approve', label: '审批通过', type: 'success' })
  }
  return actions
})
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') {
    await submitInventoryTask(row.id)
  }
  if (command === 'approve') {
    await approveInventoryTask(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
