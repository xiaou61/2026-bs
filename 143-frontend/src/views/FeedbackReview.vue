<template>
  <DataPage title="评价反馈" description="维护反馈编号、关联项目、反馈类型、评价对象和满意度，支撑居民与志愿者服务反馈闭环" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getFeedbackReviewPage, addFeedbackReview, updateFeedbackReview, deleteFeedbackReview, submitFeedbackReview, approveFeedbackReview } from '../api'

const api = { page: getFeedbackReviewPage, add: addFeedbackReview, update: updateFeedbackReview, delete: deleteFeedbackReview }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'RESIDENT', 'VOLUNTEER'].includes(role.value))
const canApprove = computed(() => ['ADMIN', 'MANAGER'].includes(role.value))
const columns = [
  { prop: 'feedbackNo', label: '反馈编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'feedbackType', label: '反馈类型' },
  { prop: 'reviewTarget', label: '评价对象' },
  { prop: 'satisfactionLevel', label: '满意度' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'feedbackNo', label: '反馈编号' },
  { prop: 'projectName', label: '关联项目' },
  { prop: 'feedbackType', label: '反馈类型' },
  { prop: 'reviewTarget', label: '评价对象' },
  { prop: 'satisfactionLevel', label: '满意度', type: 'number' },
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
  if (command === 'submit') await submitFeedbackReview(row.id)
  if (command === 'approve') await approveFeedbackReview(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
