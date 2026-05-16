<template>
  <DataPage title="成绩记录" description="成绩编号、考试名称、学员姓名、成绩分数、评定人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getExamScorePage, addExamScore, updateExamScore, deleteExamScore, submitExamScore, approveExamScore } from '../api'
const api = { page: getExamScorePage, add: addExamScore, update: updateExamScore, delete: deleteExamScore }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [{"prop": "scoreNo", "label": "成绩编号"}, {"prop": "examName", "label": "考试名称"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "scoreValue", "label": "成绩分数"}, {"prop": "evaluatorName", "label": "评定人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "scoreNo", "label": "成绩编号"}, {"prop": "examName", "label": "考试名称"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "scoreValue", "label": "成绩分数", "type": "number"}, {"prop": "evaluatorName", "label": "评定人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "草稿", "value": "DRAFT"}, {"label": "已提交", "value": "SUBMITTED"}, {"label": "已审批", "value": "APPROVED"}]}]
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'TRAINER'].includes(role.value)) actions.push({ command: 'submit', label: '提交', type: 'primary' })
  if (['ADMIN', 'MANAGER'].includes(role.value)) actions.push({ command: 'approve', label: '通过', type: 'success' })
  return actions
})
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitExamScore(row.id)
  if (command === 'approve') await approveExamScore(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




