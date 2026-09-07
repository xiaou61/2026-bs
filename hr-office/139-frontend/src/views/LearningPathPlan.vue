<template>
  <DataPage title="学习路径" description="路径编号、学员姓名、路径名称、阶段数量、完成率和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getLearningPathPlanPage, addLearningPathPlan, updateLearningPathPlan, deleteLearningPathPlan, processLearningPathPlan, finishLearningPathPlan } from '../api'
const api = { page: getLearningPathPlanPage, add: addLearningPathPlan, update: updateLearningPathPlan, delete: deleteLearningPathPlan }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [{"prop": "pathNo", "label": "路径编号"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "pathName", "label": "路径名称"}, {"prop": "stageCount", "label": "阶段数量"}, {"prop": "completedRate", "label": "完成率"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "pathNo", "label": "路径编号"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "pathName", "label": "路径名称"}, {"prop": "stageCount", "label": "阶段数量", "type": "number"}, {"prop": "completedRate", "label": "完成率", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "待处理", "value": "OPEN"}, {"label": "处理中", "value": "PROCESSING"}, {"label": "已完成", "value": "FINISHED"}]}]
const rowActions = computed(() => canManage.value ? [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}] : [])
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processLearningPathPlan(row.id)
  if (command === 'finish') await finishLearningPathPlan(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




