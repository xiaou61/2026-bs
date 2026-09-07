<template>
  <DataPage title="归还记录" description="维护归还编号、关联借用申请、归还数量和操作人，确保固定资产借还闭环" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getReturnRecordPage, addReturnRecord, updateReturnRecord, deleteReturnRecord, submitReturnRecord, approveReturnRecord } from '../api'

const api = { page: getReturnRecordPage, add: addReturnRecord, update: updateReturnRecord, delete: deleteReturnRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'BORROWER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'ASSET_ADMIN', 'AUDITOR'].includes(role.value))
const canDelete = computed(() => canManage.value)
const columns = [
  { prop: 'returnNo', label: '归还编号' },
  { prop: 'applicationNo', label: '借用申请号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'returnQty', label: '归还数量' },
  { prop: 'operatorName', label: '操作人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'returnNo', label: '归还编号' },
  { prop: 'applicationNo', label: '借用申请号' },
  { prop: 'assetName', label: '资产名称' },
  { prop: 'returnQty', label: '归还数量', type: 'number' },
  { prop: 'operatorName', label: '操作人' },
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
    await submitReturnRecord(row.id)
  }
  if (command === 'approve') {
    await approveReturnRecord(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
