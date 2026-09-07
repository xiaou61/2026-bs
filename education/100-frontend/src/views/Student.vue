<template>
  <DataPage
    title="学生档案"
    description="维护学生学号、班级、专业、联系方式和启用状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, grade: '2022级', major: '软件工程' }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addStudent, deleteStudent, getStudentPage, updateStudent } from '../api'

const statusMap = {
  0: { label: '停用', type: 'info' },
  1: { label: '在读', type: 'success' }
}
const api = { page: getStudentPage, add: addStudent, update: updateStudent, delete: deleteStudent }
const majorOptions = [
  { label: '软件工程', value: '软件工程' },
  { label: '计算机科学与技术', value: '计算机科学与技术' },
  { label: '人工智能', value: '人工智能' }
]
const statusOptions = [
  { label: '在读', value: 1 },
  { label: '停用', value: 0 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '学号/姓名/专业' },
  { type: 'input', prop: 'classId', label: '班级ID' },
  { type: 'select', prop: 'major', label: '专业', options: majorOptions },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'studentNo', label: '学号', minWidth: 140 },
  { prop: 'name', label: '姓名' },
  { prop: 'classId', label: '班级ID' },
  { prop: 'major', label: '专业', minWidth: 160 },
  { prop: 'grade', label: '年级' },
  { prop: 'phone', label: '手机号' },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'studentNo', label: '学号', required: true },
  { prop: 'name', label: '姓名', required: true },
  { prop: 'classId', label: '班级ID', type: 'number', min: 1, required: true },
  { prop: 'major', label: '专业', type: 'select', options: majorOptions, required: true },
  { prop: 'grade', label: '年级', required: true },
  { prop: 'phone', label: '手机号' },
  { prop: 'email', label: '邮箱' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
</script>
