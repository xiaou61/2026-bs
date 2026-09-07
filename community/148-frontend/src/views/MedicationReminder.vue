<template>
  <DataPage
    title="用药提醒"
    description="维护提醒编号、老人姓名、提醒类型、提醒时间和接收对象，支撑长者用药到点提醒"
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
import { getMedicationReminderPage, addMedicationReminder, updateMedicationReminder, deleteMedicationReminder, activateMedicationReminder, finishMedicationReminder } from '../api'

const api = { page: getMedicationReminderPage, add: addMedicationReminder, update: updateMedicationReminder, delete: deleteMedicationReminder }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CONSULTANT', 'CAREGIVER'].includes(role.value))
const canClose = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'reminderNo', label: '提醒编号' },
  { prop: 'elderName', label: '老人姓名', width: 140 },
  { prop: 'reminderType', label: '提醒类型', width: 180 },
  { prop: 'reminderTime', label: '提醒时间', width: 160 },
  { prop: 'receiverName', label: '接收对象', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'reminderNo', label: '提醒编号' },
  { prop: 'elderName', label: '老人姓名' },
  { prop: 'reminderType', label: '提醒类型' },
  { prop: 'reminderTime', label: '提醒时间' },
  { prop: 'receiverName', label: '接收对象' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用中', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canClose.value ? [
  { command: 'activate', label: '启用提醒', type: 'primary' },
  { command: 'finish', label: '完成提醒', type: 'success' }
] : [])
const defaults = { status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateMedicationReminder(row.id)
  if (command === 'finish') await finishMedicationReminder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
