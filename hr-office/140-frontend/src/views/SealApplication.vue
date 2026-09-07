<template>
  <DataPage title="用印申请" description="维护用印申请编号、合同主题、印章类型和用印日期，支撑申请与审批联动" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getSealApplicationPage, addSealApplication, updateSealApplication, deleteSealApplication, submitSealApplication, approveSealApplication } from '../api'

const api = { page: getSealApplicationPage, add: addSealApplication, update: updateSealApplication, delete: deleteSealApplication }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'APPLICANT'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => canManage.value)
const columns = [
  { prop: 'applicationNo', label: '申请编号' },
  { prop: 'contractTitle', label: '合同标题', width: 220 },
  { prop: 'sealType', label: '印章类型' },
  { prop: 'applicantName', label: '申请人' },
  { prop: 'useDate', label: '用印日期', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'applicationNo', label: '申请编号' },
  { prop: 'contractTitle', label: '合同标题' },
  { prop: 'sealType', label: '印章类型', type: 'select', options: [{ label: '合同专用章', value: '合同专用章' }, { label: '公司公章', value: '公司公章' }, { label: '财务专用章', value: '财务专用章' }] },
  { prop: 'applicantName', label: '申请人' },
  { prop: 'useDate', label: '用印日期' },
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
    await submitSealApplication(row.id)
  }
  if (command === 'approve') {
    await approveSealApplication(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
