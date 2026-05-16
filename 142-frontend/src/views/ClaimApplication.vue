<template>
  <DataPage title="理赔申请" description="维护申请编号、案件名称、报案渠道、申请时间和事故地点，支撑车主理赔申请提交与审批流转" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getClaimApplicationPage, addClaimApplication, updateClaimApplication, deleteClaimApplication, submitClaimApplication, approveClaimApplication } from '../api'

const api = { page: getClaimApplicationPage, add: addClaimApplication, update: updateClaimApplication, delete: deleteClaimApplication }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'APPLICANT'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canDelete = computed(() => canManage.value)
const columns = [
  { prop: 'applicationNo', label: '申请编号' },
  { prop: 'caseName', label: '案件名称', width: 180 },
  { prop: 'reportChannel', label: '报案渠道' },
  { prop: 'applicationTime', label: '申请时间', width: 160 },
  { prop: 'accidentLocation', label: '事故地点', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'applicationNo', label: '申请编号' },
  { prop: 'caseName', label: '案件名称' },
  { prop: 'reportChannel', label: '报案渠道' },
  { prop: 'applicationTime', label: '申请时间' },
  { prop: 'accidentLocation', label: '事故地点' },
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
    await submitClaimApplication(row.id)
  }
  if (command === 'approve') {
    await approveClaimApplication(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
