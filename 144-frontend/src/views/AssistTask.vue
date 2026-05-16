<template>
  <DataPage title="协助任务" description="维护任务编号、志愿者编号、任务类型、执行时间和执行人，支撑任务派发与执行闭环" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getAssistTaskPage, addAssistTask, updateAssistTask, deleteAssistTask, processAssistTask, finishAssistTask } from '../api'

const api = { page: getAssistTaskPage, add: addAssistTask, update: updateAssistTask, delete: deleteAssistTask }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'DISPATCHER'].includes(role.value))
const canProcess = computed(() => ['ADMIN', 'DISPATCHER', 'VOLUNTEER'].includes(role.value))
const columns = [
  { prop: 'taskNo', label: '任务编号' },
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'taskType', label: '任务类型' },
  { prop: 'executeTime', label: '执行时间', width: 160 },
  { prop: 'executorName', label: '执行人' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'taskNo', label: '任务编号' },
  { prop: 'volunteerNo', label: '志愿者编号' },
  { prop: 'taskType', label: '任务类型' },
  { prop: 'executeTime', label: '执行时间' },
  { prop: 'executorName', label: '执行人' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canProcess.value ? [
  { command: 'process', label: '处理', type: 'warning' },
  { command: 'finish', label: '完成', type: 'success' }
] : [])
const defaults = { status: 'PROCESSING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processAssistTask(row.id)
  if (command === 'finish') await finishAssistTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
