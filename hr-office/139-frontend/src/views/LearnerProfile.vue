<template>
  <DataPage title="学员档案" description="学员编号、学员姓名、所属部门、岗位名称、联系电话和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getLearnerProfilePage, addLearnerProfile, updateLearnerProfile, deleteLearnerProfile, activateLearnerProfile, finishLearnerProfile } from '../api'
const api = { page: getLearnerProfilePage, add: addLearnerProfile, update: updateLearnerProfile, delete: deleteLearnerProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [{"prop": "learnerNo", "label": "学员编号"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "departmentName", "label": "所属部门"}, {"prop": "positionName", "label": "岗位名称"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "learnerNo", "label": "学员编号"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "departmentName", "label": "所属部门"}, {"prop": "positionName", "label": "岗位名称"}, {"prop": "phoneNumber", "label": "联系电话"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "草稿", "value": "DRAFT"}, {"label": "启用", "value": "ACTIVE"}, {"label": "已完成", "value": "FINISHED"}]}]
const rowActions = computed(() => canManage.value ? [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}] : [])
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateLearnerProfile(row.id)
  if (command === 'finish') await finishLearnerProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




