<template>
  <DataPage
    title="课程管理"
    description="维护课程编码、授课教师、学期和检测说明。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, semester: '2025-2026-2' }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addCourse, deleteCourse, getCoursePage, updateCourse } from '../api'

const statusMap = {
  0: { label: '停用', type: 'info' },
  1: { label: '启用', type: 'success' }
}
const api = { page: getCoursePage, add: addCourse, update: updateCourse, delete: deleteCourse }
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '课程名/编码/教师' },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'courseName', label: '课程名称', minWidth: 180 },
  { prop: 'courseCode', label: '课程编码' },
  { prop: 'teacherName', label: '授课教师' },
  { prop: 'semester', label: '学期' },
  { prop: 'description', label: '说明', minWidth: 240, long: true },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'courseName', label: '课程名称', required: true },
  { prop: 'courseCode', label: '课程编码', required: true },
  { prop: 'teacherName', label: '授课教师', required: true },
  { prop: 'semester', label: '学期', required: true },
  { prop: 'description', label: '课程说明', type: 'textarea', rows: 3 },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
</script>
