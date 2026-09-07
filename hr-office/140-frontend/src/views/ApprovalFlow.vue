<template>
  <DataPage title="审批流程" description="维护合同审批流编号、当前节点、审批人和审批意见，保障签署前流程留痕" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getApprovalFlowPage, addApprovalFlow, updateApprovalFlow, deleteApprovalFlow, submitApprovalFlow, approveApprovalFlow } from '../api'

const api = { page: getApprovalFlowPage, add: addApprovalFlow, update: updateApprovalFlow, delete: deleteApprovalFlow }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL', 'APPROVER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'APPROVER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'flowNo', label: '流程编号' },
  { prop: 'contractTitle', label: '合同标题', width: 220 },
  { prop: 'currentNode', label: '当前节点' },
  { prop: 'approverName', label: '审批人' },
  { prop: 'approvalOpinion', label: '审批意见', width: 220 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'flowNo', label: '流程编号' },
  { prop: 'contractTitle', label: '合同标题' },
  { prop: 'currentNode', label: '当前节点', type: 'select', options: [{ label: '法务审查', value: '法务审查' }, { label: '用印审批', value: '用印审批' }, { label: '签署确认', value: '签署确认' }, { label: '归档复核', value: '归档复核' }] },
  { prop: 'approverName', label: '审批人' },
  { prop: 'approvalOpinion', label: '审批意见', type: 'textarea' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '草稿', value: 'DRAFT' }, { label: '已提交', value: 'SUBMITTED' }, { label: '已审批', value: 'APPROVED' }] }
]
const rowActions = computed(() => {
  const actions = []
  if (canManage.value) {
    actions.push({ command: 'submit', label: '提交流转', type: 'warning' })
  }
  if (canApprove.value) {
    actions.push({ command: 'approve', label: '审批通过', type: 'success' })
  }
  return actions
})
const defaults = { status: 'DRAFT' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') {
    await submitApprovalFlow(row.id)
  }
  if (command === 'approve') {
    await approveApprovalFlow(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
