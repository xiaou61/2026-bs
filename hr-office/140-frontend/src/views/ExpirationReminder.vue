<template>
  <DataPage title="到期提醒" description="维护合同到期日期、提醒方式和相对方信息，支持合同履约前的续签预警" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getExpirationReminderPage, addExpirationReminder, updateExpirationReminder, deleteExpirationReminder, activateExpirationReminder, finishExpirationReminder } from '../api'

const api = { page: getExpirationReminderPage, add: addExpirationReminder, update: updateExpirationReminder, delete: deleteExpirationReminder }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'LEGAL'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [
  { prop: 'reminderNo', label: '提醒编号' },
  { prop: 'contractTitle', label: '合同标题', width: 220 },
  { prop: 'counterpartyName', label: '相对方名称', width: 180 },
  { prop: 'expiryDate', label: '到期日期', width: 160 },
  { prop: 'reminderMethod', label: '提醒方式' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'reminderNo', label: '提醒编号' },
  { prop: 'contractTitle', label: '合同标题' },
  { prop: 'counterpartyName', label: '相对方名称' },
  { prop: 'expiryDate', label: '到期日期' },
  { prop: 'reminderMethod', label: '提醒方式', type: 'select', options: [{ label: '邮件提醒', value: '邮件提醒' }, { label: '短信提醒', value: '短信提醒' }, { label: '站内消息', value: '站内消息' }] },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '启用', value: 'ACTIVE' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canManage.value ? [{ command: 'activate', label: '启用', type: 'success' }, { command: 'finish', label: '完成', type: 'primary' }] : [])
const defaults = { reminderMethod: '邮件提醒', status: 'ACTIVE' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') {
    await activateExpirationReminder(row.id)
  }
  if (command === 'finish') {
    await finishExpirationReminder(row.id)
  }
  ElMessage.success('操作成功')
  refresh()
}
</script>
