<template>
  <DataPage
    title="处罚决定"
    description="维护处罚编号、处罚类型、处罚对象和处罚金额，支撑执法处罚文书记录与审核"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canManage"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getPenaltyDecisionPage, addPenaltyDecision, updatePenaltyDecision, deletePenaltyDecision, submitPenaltyDecision, approvePenaltyDecision } from '../api'

const api = { page: getPenaltyDecisionPage, add: addPenaltyDecision, update: updatePenaltyDecision, delete: deletePenaltyDecision }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'OFFICER', 'SUPERVISOR'].includes(role.value))
const canSubmit = computed(() => ['ADMIN', 'OFFICER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'SUPERVISOR'].includes(role.value))
const columns = [
  { prop: 'penaltyNo', label: '处罚编号' },
  { prop: 'complaintTitle', label: '投诉标题', width: 180 },
  { prop: 'penaltyType', label: '处罚类型', width: 140 },
  { prop: 'penaltyTarget', label: '处罚对象', width: 180 },
  { prop: 'penaltyAmount', label: '处罚金额', width: 120 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'penaltyNo', label: '处罚编号' },
  { prop: 'complaintTitle', label: '投诉标题' },
  { prop: 'penaltyType', label: '处罚类型' },
  { prop: 'penaltyTarget', label: '处罚对象' },
  { prop: 'penaltyAmount', label: '处罚金额', type: 'number' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '待提交', value: 'OPEN' },
      { label: '已提交', value: 'SUBMITTED' },
      { label: '已审批', value: 'APPROVED' }
    ]
  }
]
const rowActions = computed(() => {
  const actions = []
  if (canSubmit.value) actions.push({ command: 'submit', label: '提交处罚', type: 'primary' })
  if (canApprove.value) actions.push({ command: 'approve', label: '审批通过', type: 'success' })
  return actions
})
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitPenaltyDecision(row.id)
  if (command === 'approve') await approvePenaltyDecision(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
