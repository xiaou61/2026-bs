<template>
  <DataPage title="盘点明细" description="维护盘点明细编号、所属任务、核验人和差异说明，沉淀盘点过程留痕" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getInventoryRecordPage, addInventoryRecord, updateInventoryRecord, deleteInventoryRecord, submitInventoryRecord, approveInventoryRecord } from '../api'

const api = { page: getInventoryRecordPage, add: addInventoryRecord, update: updateInventoryRecord, delete: deleteInventoryRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'ASSET_ADMIN'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'AUDITOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'recordNo', label: '明细编号' },
  { prop: 'taskNo', label: '任务编号' },
  { prop: 'checkerName', label: '核验人' },
  { prop: 'differenceNote', label: '差异说明', width: 220 },
  { prop: 'checkTime', label: '核验时间', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'recordNo', label: '明细编号' },
  { prop: 'taskNo', label: '任务编号' },
  { prop: 'checkerName', label: '核验人' },
  { prop: 'differenceNote', label: '差异说明', type: 'textarea' },
  { prop: 'checkTime', label: '核验时间' },
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
    await submitInventoryRecord(row.id)
  }
  if (command === 'approve') {
    await approveInventoryRecord(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
