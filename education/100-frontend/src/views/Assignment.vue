<template>
  <DataPage
    title="作业任务"
    description="教师发布课程论文、实验报告和论文初稿检测任务。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, assignmentType: '课程论文', thresholdScore: 75 }"
    dialog-width="760px"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addAssignment, deleteAssignment, getAssignmentPage, updateAssignment } from '../api'

const statusMap = {
  0: { label: '草稿', type: 'info' },
  1: { label: '进行中', type: 'success' },
  2: { label: '已结束', type: 'primary' }
}
const api = { page: getAssignmentPage, add: addAssignment, update: updateAssignment, delete: deleteAssignment }
const typeOptions = [
  { label: '课程论文', value: '课程论文' },
  { label: '实验报告', value: '实验报告' },
  { label: '论文初稿', value: '论文初稿' },
  { label: '竞赛材料', value: '竞赛材料' }
]
const statusOptions = [
  { label: '草稿', value: 0 },
  { label: '进行中', value: 1 },
  { label: '已结束', value: 2 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '标题/类型' },
  { type: 'input', prop: 'courseId', label: '课程ID' },
  { type: 'select', prop: 'assignmentType', label: '类型', options: typeOptions },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'title', label: '作业标题', minWidth: 180 },
  { prop: 'courseId', label: '课程ID' },
  { prop: 'assignmentType', label: '类型' },
  { prop: 'deadline', label: '截止时间', minWidth: 170 },
  { prop: 'thresholdScore', label: '阈值分' },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'courseId', label: '课程ID', type: 'number', min: 1, required: true },
  { prop: 'title', label: '作业标题', required: true },
  { prop: 'assignmentType', label: '类型', type: 'select', options: typeOptions, required: true },
  { prop: 'deadline', label: '截止日期', type: 'date', required: true },
  { prop: 'thresholdScore', label: '阈值分', type: 'number', min: 0, max: 100, precision: 2 },
  { prop: 'description', label: '任务说明', type: 'textarea', rows: 4 },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
</script>
