<template>
  <DataPage title="胜任力画像" description="画像编号、学员姓名、能力等级、差距项数、评估人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getCompetencyProfilePage, addCompetencyProfile, updateCompetencyProfile, deleteCompetencyProfile, processCompetencyProfile, finishCompetencyProfile } from '../api'
const api = { page: getCompetencyProfilePage, add: addCompetencyProfile, update: updateCompetencyProfile, delete: deleteCompetencyProfile }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [{"prop": "profileNo", "label": "画像编号"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "competencyLevel", "label": "能力等级"}, {"prop": "gapCount", "label": "差距项数"}, {"prop": "evaluatorName", "label": "评估人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "profileNo", "label": "画像编号"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "competencyLevel", "label": "能力等级"}, {"prop": "gapCount", "label": "差距项数", "type": "number"}, {"prop": "evaluatorName", "label": "评估人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "待处理", "value": "OPEN"}, {"label": "处理中", "value": "PROCESSING"}, {"label": "已完成", "value": "FINISHED"}]}]
const rowActions = computed(() => canManage.value ? [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}] : [])
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processCompetencyProfile(row.id)
  if (command === 'finish') await finishCompetencyProfile(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




