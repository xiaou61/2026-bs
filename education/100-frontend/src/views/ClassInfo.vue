<template>
  <DataPage
    title="班级管理"
    description="维护班级、专业、年级、人数和负责教师。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, grade: '2022级' }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addClass, deleteClass, getClassPage, updateClass } from '../api'

const statusMap = {
  0: { label: '停用', type: 'info' },
  1: { label: '启用', type: 'success' }
}
const api = { page: getClassPage, add: addClass, update: updateClass, delete: deleteClass }
const majorOptions = [
  { label: '软件工程', value: '软件工程' },
  { label: '计算机科学与技术', value: '计算机科学与技术' },
  { label: '人工智能', value: '人工智能' }
]
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '班级/教师' },
  { type: 'select', prop: 'major', label: '专业', options: majorOptions },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'className', label: '班级名称', minWidth: 160 },
  { prop: 'major', label: '专业', minWidth: 160 },
  { prop: 'grade', label: '年级' },
  { prop: 'teacherName', label: '负责教师' },
  { prop: 'studentCount', label: '人数' },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'className', label: '班级名称', required: true },
  { prop: 'major', label: '专业', type: 'select', options: majorOptions, required: true },
  { prop: 'grade', label: '年级', required: true },
  { prop: 'teacherName', label: '负责教师', required: true },
  { prop: 'studentCount', label: '人数', type: 'number', min: 0 },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
</script>
