<template>
  <DataPage
    title="文本提交"
    description="提交课程作业文本、引用说明和附件地址，系统自动记录字数。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 0 }"
    dialog-width="820px"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addSubmission, deleteSubmission, getSubmissionPage, updateSubmission } from '../api'

const statusMap = {
  0: { label: '待检测', type: 'warning' },
  1: { label: '已提交', type: 'primary' },
  2: { label: '低风险', type: 'success' },
  3: { label: '高风险', type: 'danger' }
}
const api = { page: getSubmissionPage, add: addSubmission, update: updateSubmission, delete: deleteSubmission }
const statusOptions = [
  { label: '待检测', value: 0 },
  { label: '已提交', value: 1 },
  { label: '低风险', value: 2 },
  { label: '高风险', value: 3 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '标题/正文/引用' },
  { type: 'input', prop: 'assignmentId', label: '作业ID' },
  { type: 'input', prop: 'studentId', label: '学生ID' },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'title', label: '提交标题', minWidth: 180 },
  { prop: 'assignmentId', label: '作业ID' },
  { prop: 'studentId', label: '学生ID' },
  { prop: 'content', label: '正文摘要', minWidth: 260, long: true },
  { prop: 'wordCount', label: '字数' },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'submitTime', label: '提交时间', minWidth: 170 }
]
const formFields = [
  { prop: 'assignmentId', label: '作业ID', type: 'number', min: 1, required: true },
  { prop: 'studentId', label: '学生ID', type: 'number', min: 1, required: true },
  { prop: 'title', label: '提交标题', required: true },
  { prop: 'content', label: '正文内容', type: 'textarea', rows: 8, required: true },
  { prop: 'citationNote', label: '引用说明', type: 'textarea', rows: 3 },
  { prop: 'attachmentUrl', label: '附件地址' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
</script>
