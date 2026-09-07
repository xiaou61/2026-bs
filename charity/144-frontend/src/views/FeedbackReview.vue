<template>
  <DataPage title="评价反馈" description="维护反馈编号、志愿者编号、满意度、反馈时间和评价对象，支撑服务体验复盘与改进" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getFeedbackReviewPage, addFeedbackReview, updateFeedbackReview, deleteFeedbackReview, activateFeedbackReview, finishFeedbackReview } from '../api'

const api = { page: getFeedbackReviewPage, add: addFeedbackReview, update: updateFeedbackReview, delete: deleteFeedbackReview }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAVELER', 'DISPATCHER'].includes(role.value))
const columns = [
  { prop: 'feedbackNo', label: '反馈编号' },
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'satisfactionLevel', label: '满意度' },
  { prop: 'feedbackTime', label: '反馈时间', width: 160 },
  { prop: 'reviewTarget', label: '评价对象', width: 180 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'feedbackNo', label: '反馈编号' },
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'satisfactionLevel', label: '满意度', type: 'number' },
  { prop: 'feedbackTime', label: '反馈时间' },
  { prop: 'reviewTarget', label: '评价对象' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [
  { command: 'activate', label: '启用', type: 'success' },
  { command: 'finish', label: '完成', type: 'primary' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateFeedbackReview(row.id)
  if (command === 'finish') await finishFeedbackReview(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
