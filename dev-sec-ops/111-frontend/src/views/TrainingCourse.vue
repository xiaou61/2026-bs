<template>
  <DataPage title="培训课程" description="安全意识课程、课时和发布状态管理" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" @row-action="handleAction" />
</template>

<script setup>
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getTrainingCoursePage, addTrainingCourse, updateTrainingCourse, deleteTrainingCourse, publishTrainingCourse, archiveTrainingCourse } from '../api'
const api = { page: getTrainingCoursePage, add: addTrainingCourse, update: updateTrainingCourse, delete: deleteTrainingCourse }
const columns = [{"prop": "courseName", "label": "课程名称"}, {"prop": "courseCode", "label": "课程编码"}, {"prop": "categoryName", "label": "分类"}, {"prop": "teacherName", "label": "讲师"}, {"prop": "lessonMinutes", "label": "课时分钟"}, {"prop": "status", "label": "状态"}]
const formFields = [{"prop": "courseName", "label": "课程名称"}, {"prop": "courseCode", "label": "课程编码"}, {"prop": "categoryName", "label": "分类"}, {"prop": "teacherName", "label": "讲师"}, {"prop": "lessonMinutes", "label": "课时分钟", "type": "number"}, {"prop": "status", "label": "状态", "type": "select", "options": [{"label": "DRAFT", "value": "DRAFT"}, {"label": "PUBLISHED", "value": "PUBLISHED"}, {"label": "ARCHIVED", "value": "ARCHIVED"}]}]
const rowActions = [{"command": "publish", "label": "发布", "type": "primary"}, {"command": "archive", "label": "归档", "type": "primary"}]
const defaults = {"status": "DRAFT"}
const handleAction = async ({ command, row, refresh }) => {
  if (command === 'publish') await publishTrainingCourse(row.id)
  if (command === 'archive') await archiveTrainingCourse(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
