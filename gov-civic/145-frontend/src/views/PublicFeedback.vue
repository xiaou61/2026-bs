<template>
  <DataPage
    title="公众回访"
    description="维护回访编号、回访主题、回访方式和回访时间，支撑公众满意度回访与效果复盘"
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
import { getPublicFeedbackPage, addPublicFeedback, updatePublicFeedback, deletePublicFeedback, processPublicFeedback, finishPublicFeedback } from '../api'

const api = { page: getPublicFeedbackPage, add: addPublicFeedback, update: updatePublicFeedback, delete: deletePublicFeedback }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CITIZEN', 'SUPERVISOR'].includes(role.value))
const canProcess = computed(() => ['ADMIN', 'SUPERVISOR'].includes(role.value))
const columns = [
  { prop: 'feedbackNo', label: '回访编号' },
  { prop: 'complaintTitle', label: '投诉标题', width: 180 },
  { prop: 'feedbackTopic', label: '回访主题', width: 180 },
  { prop: 'feedbackChannel', label: '回访方式', width: 140 },
  { prop: 'feedbackTime', label: '回访时间', width: 160 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'feedbackNo', label: '回访编号' },
  { prop: 'complaintTitle', label: '投诉标题' },
  { prop: 'feedbackTopic', label: '回访主题' },
  { prop: 'feedbackChannel', label: '回访方式' },
  { prop: 'feedbackTime', label: '回访时间' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '回访中', value: 'PROCESSING' }, { label: '回访完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canProcess.value ? [
  { command: 'process', label: '回访中', type: 'primary' },
  { command: 'finish', label: '回访完成', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processPublicFeedback(row.id)
  if (command === 'finish') await finishPublicFeedback(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
