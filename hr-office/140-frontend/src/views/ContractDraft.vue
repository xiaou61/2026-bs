<template>
  <DataPage title="合同草稿" description="维护合同草稿编号、所用模板、合同标题和提交流转状态，支撑起草与审批衔接" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getContractDraftPage, addContractDraft, updateContractDraft, deleteContractDraft, submitContractDraft, approveContractDraft } from '../api'

const api = { page: getContractDraftPage, add: addContractDraft, update: updateContractDraft, delete: deleteContractDraft }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'APPLICANT'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => canManage.value)
const columns = [
  { prop: 'draftNo', label: '草稿编号' },
  { prop: 'templateName', label: '模板名称' },
  { prop: 'contractTitle', label: '合同标题', width: 220 },
  { prop: 'applicantName', label: '申请人' },
  { prop: 'submitTime', label: '提交时间', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'draftNo', label: '草稿编号' },
  { prop: 'templateName', label: '模板名称' },
  { prop: 'contractTitle', label: '合同标题' },
  { prop: 'applicantName', label: '申请人' },
  { prop: 'submitTime', label: '提交时间' },
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
    await submitContractDraft(row.id)
  }
  if (command === 'approve') {
    await approveContractDraft(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
