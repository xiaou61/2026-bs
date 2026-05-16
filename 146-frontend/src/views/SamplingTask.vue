<template>
  <DataPage
    title="抽样任务"
    description="安排现场抽样任务、时间地点与抽检员分工"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canDelete"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addSamplingTask, approveSamplingTask, deleteSamplingTask, getSamplingTaskPage, submitSamplingTask, updateSamplingTask } from '../api'
import { useUserStore } from '../store/user'

const api = { page: getSamplingTaskPage, add: addSamplingTask, update: updateSamplingTask, delete: deleteSamplingTask }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role)
const canManage = computed(() => ['ADMIN', 'INSPECTOR'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'INSPECTOR'].includes(role.value)) actions.push({ command: 'submit', label: '提交', type: 'success' })
  if (['ADMIN', 'REVIEWER'].includes(role.value)) actions.push({ command: 'approve', label: '审核通过', type: 'warning' })
  return actions
})
const statusOptions = [
  { label: '已提交', value: 'SUBMITTED' },
  { label: '已审批', value: 'APPROVED' },
  { label: '已完成', value: 'FINISHED' }
]
const columns = [
  { prop: 'taskNo', label: '任务编号' },
  { prop: 'taskTitle', label: '任务主题' },
  { prop: 'samplingLocation', label: '抽样地点', width: 180 },
  { prop: 'samplingTime', label: '抽样时间' },
  { prop: 'inspectorName', label: '抽检员' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'taskNo', label: '任务编号' },
  { prop: 'taskTitle', label: '任务主题' },
  { prop: 'samplingLocation', label: '抽样地点', type: 'textarea' },
  { prop: 'samplingTime', label: '抽样时间' },
  { prop: 'inspectorName', label: '抽检员' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const defaults = { status: 'SUBMITTED' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitSamplingTask(row.id)
  if (command === 'approve') await approveSamplingTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
