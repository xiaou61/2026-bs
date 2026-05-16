<template>
  <DataPage title="学习提醒" description="提醒编号、学员姓名、提醒类型、提醒时间、通知渠道和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getLearningReminderPage, addLearningReminder, updateLearningReminder, deleteLearningReminder, processLearningReminder, finishLearningReminder } from '../api'
const api = { page: getLearningReminderPage, add: addLearningReminder, update: updateLearningReminder, delete: deleteLearningReminder }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [{"prop": "reminderNo", "label": "提醒编号"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "reminderType", "label": "提醒类型"}, {"prop": "remindTime", "label": "提醒时间"}, {"prop": "channelName", "label": "通知渠道"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "reminderNo", "label": "提醒编号"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "reminderType", "label": "提醒类型"}, {"prop": "remindTime", "label": "提醒时间"}, {"prop": "channelName", "label": "通知渠道"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "待处理", "value": "OPEN"}, {"label": "处理中", "value": "PROCESSING"}, {"label": "已完成", "value": "FINISHED"}]}]
const rowActions = computed(() => canManage.value ? [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}] : [])
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processLearningReminder(row.id)
  if (command === 'finish') await finishLearningReminder(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




