<template>
  <DataPage title="技能标签" description="标签编号、标签名称、分类名称、标签等级、负责人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getSkillTagPage, addSkillTag, updateSkillTag, deleteSkillTag, submitSkillTag, approveSkillTag } from '../api'
const api = { page: getSkillTagPage, add: addSkillTag, update: updateSkillTag, delete: deleteSkillTag }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [{"prop": "tagNo", "label": "标签编号"}, {"prop": "tagName", "label": "标签名称"}, {"prop": "categoryName", "label": "分类名称"}, {"prop": "tagLevel", "label": "标签等级"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "tagNo", "label": "标签编号"}, {"prop": "tagName", "label": "标签名称"}, {"prop": "categoryName", "label": "分类名称"}, {"prop": "tagLevel", "label": "标签等级"}, {"prop": "ownerName", "label": "负责人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "草稿", "value": "DRAFT"}, {"label": "已提交", "value": "SUBMITTED"}, {"label": "已审批", "value": "APPROVED"}]}]
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value)) actions.push({ command: 'submit', label: '提交', type: 'primary' })
  if (['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value)) actions.push({ command: 'approve', label: '通过', type: 'success' })
  return actions
})
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitSkillTag(row.id)
  if (command === 'approve') await approveSkillTag(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




