<template>
  <DataPage title="维修记录" description="维护维修编号、故障位置、工时和联系人，跟踪固定资产维修处理过程" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getRepairRecordPage, addRepairRecord, updateRepairRecord, deleteRepairRecord, submitRepairRecord, approveRepairRecord } from '../api'

const api = { page: getRepairRecordPage, add: addRepairRecord, update: updateRepairRecord, delete: deleteRepairRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'ASSET_ADMIN', 'BORROWER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'].includes(role.value))
const canDelete = computed(() => canManage.value)
const columns = [
  { prop: 'repairNo', label: '维修编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'faultLocation', label: '故障位置' },
  { prop: 'repairHours', label: '维修工时' },
  { prop: 'contactName', label: '联系人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'repairNo', label: '维修编号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'faultLocation', label: '故障位置' },
  { prop: 'repairHours', label: '维修工时', type: 'number' },
  { prop: 'contactName', label: '联系人' },
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
    await submitRepairRecord(row.id)
  }
  if (command === 'approve') {
    await approveRepairRecord(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
