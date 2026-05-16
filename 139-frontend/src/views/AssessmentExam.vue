<template>
  <DataPage title="考核考试" description="考试编号、考试名称、关联课程、考试日期、参与人数和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getAssessmentExamPage, addAssessmentExam, updateAssessmentExam, deleteAssessmentExam, processAssessmentExam, finishAssessmentExam } from '../api'
const api = { page: getAssessmentExamPage, add: addAssessmentExam, update: updateAssessmentExam, delete: deleteAssessmentExam }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [{"prop": "examNo", "label": "考试编号"}, {"prop": "examName", "label": "考试名称"}, {"prop": "courseName", "label": "关联课程"}, {"prop": "examDate", "label": "考试日期"}, {"prop": "participantCount", "label": "参与人数"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "examNo", "label": "考试编号"}, {"prop": "examName", "label": "考试名称"}, {"prop": "courseName", "label": "关联课程"}, {"prop": "examDate", "label": "考试日期"}, {"prop": "participantCount", "label": "参与人数", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "待处理", "value": "OPEN"}, {"label": "处理中", "value": "PROCESSING"}, {"label": "已完成", "value": "FINISHED"}]}]
const rowActions = computed(() => canManage.value ? [{"command": "process", "label": "处理", "type": "warning"}, {"command": "finish", "label": "完成", "type": "success"}] : [])
const defaults = {"status": "OPEN"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processAssessmentExam(row.id)
  if (command === 'finish') await finishAssessmentExam(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




