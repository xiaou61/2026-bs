<template>
  <DataPage
    ref="pageRef"
    title="面试反馈"
    description="面试官填写评分、优势、不足、建议和面试结果。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ resultStatus: 0 }"
    :readonly="readonly"
  />
</template>

<script setup>
import { computed, ref } from 'vue'
import DataPage from '../components/DataPage.vue'
import { addFeedback, deleteFeedback, getFeedbackPage, updateFeedback } from '../api'
import { useUserStore } from '../store/user'

const pageRef = ref()
const userStore = useUserStore()
const isInterviewer = computed(() => userStore.user?.role === 'INTERVIEWER')
const readonly = computed(() => !isInterviewer.value)

const resultMap = { 0: { label: '待定', type: 'warning' }, 1: { label: '通过', type: 'success' }, 2: { label: '不通过', type: 'danger' } }
const api = { page: getFeedbackPage, add: addFeedback, update: updateFeedback, delete: deleteFeedback }
const resultOptions = [{ label: '待定', value: 0 }, { label: '通过', value: 1 }, { label: '不通过', value: 2 }]

const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'input', prop: 'planId', label: '面试ID' }, { type: 'select', prop: 'resultStatus', label: '结果', options: resultOptions }]
const columns = computed(() => {
  const items = [{ prop: 'planId', label: '面试ID' }, { prop: 'score', label: '评分' }, { prop: 'resultStatus', label: '结果', map: resultMap }, { prop: 'strengths', label: '优势', minWidth: 220, long: true }, { prop: 'suggestion', label: '建议', minWidth: 220, long: true }]
  if (!isInterviewer.value) items.splice(1, 0, { prop: 'interviewerId', label: '面试官ID' })
  return items
})
const formFields = [{ prop: 'planId', label: '面试ID', type: 'number', min: 1, required: true }, { prop: 'score', label: '评分', type: 'number', min: 0, max: 100, precision: 2 }, { prop: 'resultStatus', label: '结果', type: 'select', options: resultOptions }, { prop: 'strengths', label: '优势', type: 'textarea', rows: 3 }, { prop: 'weaknesses', label: '不足', type: 'textarea', rows: 3 }, { prop: 'suggestion', label: '建议', type: 'textarea', rows: 3 }]
</script>
