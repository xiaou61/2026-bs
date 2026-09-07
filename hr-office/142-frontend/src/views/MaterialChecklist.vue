<template>
  <DataPage title="材料清单" description="维护材料编号、报案编号、材料类型和提交时间，支撑理赔补件、材料归档和审核流转" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getMaterialChecklistPage, addMaterialChecklist, updateMaterialChecklist, deleteMaterialChecklist, submitMaterialChecklist, approveMaterialChecklist } from '../api'

const api = { page: getMaterialChecklistPage, add: addMaterialChecklist, update: updateMaterialChecklist, delete: deleteMaterialChecklist }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'APPLICANT'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => canManage.value)
const columns = [
  { prop: 'checklistNo', label: '清单编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'materialType', label: '材料类型' },
  { prop: 'materialDesc', label: '材料说明', width: 220 },
  { prop: 'submitTime', label: '提交时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'checklistNo', label: '清单编号' },
  { prop: 'reportNo', label: '报案编号' },
  { prop: 'materialType', label: '材料类型' },
  { prop: 'materialDesc', label: '材料说明', type: 'textarea' },
  { prop: 'submitTime', label: '提交时间' },
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
  if (command === 'submit') {
    await submitMaterialChecklist(row.id)
  }
  if (command === 'approve') {
    await approveMaterialChecklist(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
