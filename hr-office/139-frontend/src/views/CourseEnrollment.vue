<template>
  <DataPage title="选课报名" description="报名编号、课程名称、学员姓名、报名时间、审批人和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canEdit" :can-edit="canEdit" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getCourseEnrollmentPage, addCourseEnrollment, updateCourseEnrollment, deleteCourseEnrollment, submitCourseEnrollment, approveCourseEnrollment } from '../api'
const api = { page: getCourseEnrollmentPage, add: addCourseEnrollment, update: updateCourseEnrollment, delete: deleteCourseEnrollment }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canEdit = computed(() => ['ADMIN', 'EMPLOYEE'].includes(role.value))
const canDelete = computed(() => ['ADMIN', 'EMPLOYEE'].includes(role.value))
const columns = [{"prop": "enrollmentNo", "label": "报名编号"}, {"prop": "courseName", "label": "课程名称"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "applyTime", "label": "报名时间"}, {"prop": "approverName", "label": "审批人"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "enrollmentNo", "label": "报名编号"}, {"prop": "courseName", "label": "课程名称"}, {"prop": "learnerName", "label": "学员姓名"}, {"prop": "applyTime", "label": "报名时间"}, {"prop": "approverName", "label": "审批人"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "草稿", "value": "DRAFT"}, {"label": "已提交", "value": "SUBMITTED"}, {"label": "已审批", "value": "APPROVED"}]}]
const rowActions = computed(() => {
  const actions = []
  if (['ADMIN', 'EMPLOYEE'].includes(role.value)) actions.push({ command: 'submit', label: '提交', type: 'primary' })
  if (['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value)) actions.push({ command: 'approve', label: '通过', type: 'success' })
  return actions
})
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'submit') await submitCourseEnrollment(row.id)
  if (command === 'approve') await approveCourseEnrollment(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




