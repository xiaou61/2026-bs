<template>
  <DataPage title="培训项目" description="项目编号、项目名称、目标岗位、组织单位、课时安排和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getTrainingProgramPage, addTrainingProgram, updateTrainingProgram, deleteTrainingProgram, activateTrainingProgram, finishTrainingProgram } from '../api'
const api = { page: getTrainingProgramPage, add: addTrainingProgram, update: updateTrainingProgram, delete: deleteTrainingProgram }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [{"prop": "programNo", "label": "项目编号"}, {"prop": "programName", "label": "项目名称"}, {"prop": "targetRole", "label": "目标岗位"}, {"prop": "organizerName", "label": "组织单位"}, {"prop": "sessionCount", "label": "课时安排"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "programNo", "label": "项目编号"}, {"prop": "programName", "label": "项目名称"}, {"prop": "targetRole", "label": "目标岗位"}, {"prop": "organizerName", "label": "组织单位"}, {"prop": "sessionCount", "label": "课时安排", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "草稿", "value": "DRAFT"}, {"label": "启用", "value": "ACTIVE"}, {"label": "已完成", "value": "FINISHED"}]}]
const rowActions = computed(() => canManage.value ? [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}] : [])
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateTrainingProgram(row.id)
  if (command === 'finish') await finishTrainingProgram(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




