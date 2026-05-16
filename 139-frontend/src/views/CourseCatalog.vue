<template>
  <DataPage title="课程目录" description="课程编号、课程名称、讲师姓名、学时、授课方式和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canDelete" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getCourseCatalogPage, addCourseCatalog, updateCourseCatalog, deleteCourseCatalog, activateCourseCatalog, finishCourseCatalog } from '../api'
const api = { page: getCourseCatalogPage, add: addCourseCatalog, update: updateCourseCatalog, delete: deleteCourseCatalog }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'TRAINER', 'MANAGER'].includes(role.value))
const canDelete = computed(() => role.value === 'ADMIN')
const columns = [{"prop": "courseNo", "label": "课程编号"}, {"prop": "courseName", "label": "课程名称"}, {"prop": "lecturerName", "label": "讲师姓名"}, {"prop": "creditHours", "label": "学时"}, {"prop": "deliveryMode", "label": "授课方式"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "courseNo", "label": "课程编号"}, {"prop": "courseName", "label": "课程名称"}, {"prop": "lecturerName", "label": "讲师姓名"}, {"prop": "creditHours", "label": "学时", "type": "number"}, {"prop": "deliveryMode", "label": "授课方式"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "草稿", "value": "DRAFT"}, {"label": "启用", "value": "ACTIVE"}, {"label": "已完成", "value": "FINISHED"}]}]
const rowActions = computed(() => canManage.value ? [{"command": "activate", "label": "启用", "type": "success"}, {"command": "finish", "label": "完成", "type": "primary"}] : [])
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'activate') await activateCourseCatalog(row.id)
  if (command === 'finish') await finishCourseCatalog(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>




