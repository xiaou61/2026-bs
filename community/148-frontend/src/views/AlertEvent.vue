<template>
  <DataPage
    title="预警事件"
    description="维护事件编号、老人姓名、上报时间、事件类型和处理建议，支撑异常情况联动响应"
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
import { getAlertEventPage, addAlertEvent, updateAlertEvent, deleteAlertEvent, processAlertEvent, finishAlertEvent } from '../api'

const api = { page: getAlertEventPage, add: addAlertEvent, update: updateAlertEvent, delete: deleteAlertEvent }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CONSULTANT', 'CAREGIVER'].includes(role.value))
const canClose = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'alertNo', label: '事件编号' },
  { prop: 'elderName', label: '老人姓名', width: 140 },
  { prop: 'reportTime', label: '上报时间', width: 160 },
  { prop: 'alertType', label: '事件类型', width: 160 },
  { prop: 'handlingSuggestion', label: '处理建议', width: 220 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'alertNo', label: '事件编号' },
  { prop: 'elderName', label: '老人姓名' },
  { prop: 'reportTime', label: '上报时间' },
  { prop: 'alertType', label: '事件类型' },
  { prop: 'handlingSuggestion', label: '处理建议', type: 'textarea' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '预警中', value: 'WARNING' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canClose.value ? [
  { command: 'process', label: '跟进处理', type: 'primary' },
  { command: 'finish', label: '关闭事件', type: 'success' }
] : [])
const defaults = { status: 'WARNING' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processAlertEvent(row.id)
  if (command === 'finish') await finishAlertEvent(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
