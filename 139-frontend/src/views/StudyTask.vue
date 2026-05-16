<template>
  <DataPage title="学习任务" description="任务编号、所属路径、任务名称、截止时间、执行人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canEdit" :can-edit="canEdit" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getStudyTaskPage, addStudyTask, updateStudyTask, deleteStudyTask, submitStudyTask, approveStudyTask } from '../api'
const api = { page: getStudyTaskPage, add: addStudyTask, update: updateStudyTask, delete: deleteStudyTask }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canEdit = computed(() => ['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "pathName", "label": "所属路径"}, {"prop": "taskName", "label": "任务名称"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "assigneeName", "label": "执行人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "taskNo", "label": "任务编号"}, {"prop": "pathName", "label": "所属路径"}, {"prop": "taskName", "label": "任务名称"}, {"prop": "deadlineTime", "label": "截止时间"}, {"prop": "assigneeName", "label": "执行人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "草稿", "value": "DRAFT"}, {"label": "已提交", "value": "SUBMITTED"}, {"label": "已审批", "value": "APPROVED"}]}]
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'TRAINER', 'EMPLOYEE'].includes(role.value)) actions.push({ command: 'submit', label: '提交', type: 'primary' })
  if (['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value)) actions.push({ command: 'approve', label: '通过', type: 'success' })
  return actions
})
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitStudyTask(row.id)
  if (command === 'approve') await approveStudyTask(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




