<template>
  <DataPage ref="pageRef" title="面试排期" description="安排候选人与岗位面试，支持确认、取消和完成。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ interviewType: '视频面试' }" :readonly="readonly" @row-action="handleAction" />
</template>

<script setup>
import { computed, ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addInterview, cancelInterview, confirmInterview, deleteInterview, finishInterview, getInterviewPage, updateInterview } from '../api'
import { useUserStore } from '../store/user'

const pageRef = ref()
const userStore = useUserStore()
const isInterviewer = computed(() => userStore.user?.role === 'INTERVIEWER')
const readonly = computed(() => isInterviewer.value)
const statusMap = { 0: { label: '待确认', type: 'warning' }, 1: { label: '已确认', type: 'primary' }, 2: { label: '已取消', type: 'danger' }, 3: { label: '已完成', type: 'success' } }
const api = { page: getInterviewPage, add: addInterview, update: updateInterview, delete: deleteInterview }
const typeOptions = [{ label: '视频面试', value: '视频面试' }, { label: '现场面试', value: '现场面试' }]
const statusOptions = [{ label: '待确认', value: 0 }, { label: '已确认', value: 1 }, { label: '已取消', value: 2 }, { label: '已完成', value: 3 }]
const filters = computed(() => {
  const items = [{ type: 'select', prop: 'status', label: '状态', options: statusOptions }]
  if (!isInterviewer.value) {
    items.unshift({ type: 'input', prop: 'jobId', label: '岗位ID' })
    items.unshift({ type: 'input', prop: 'candidateId', label: '候选人ID' })
  }
  return items
})
const columns = computed(() => {
  const items = [
    { prop: 'candidateId', label: '候选人ID' },
    { prop: 'jobId', label: '岗位ID' },
    { prop: 'interviewTime', label: '面试时间', minWidth: 170 },
    { prop: 'interviewType', label: '方式' },
    { prop: 'status', label: '状态', map: statusMap }
  ]
  if (!isInterviewer.value) items.splice(2, 0, { prop: 'interviewerId', label: '面试官ID' })
  return items
})
const formFields = [
  { prop: 'candidateId', label: '候选人ID', type: 'number', min: 1, required: true },
  { prop: 'jobId', label: '岗位ID', type: 'number', min: 1, required: true },
  { prop: 'interviewerId', label: '面试官ID', type: 'number', min: 1 },
  { prop: 'interviewTime', label: '面试时间', type: 'datetime' },
  { prop: 'interviewType', label: '面试方式', type: 'select', options: typeOptions },
  { prop: 'meetingAddress', label: '面试地址' },
  { prop: 'remark', label: '备注', type: 'textarea', rows: 3 }
]
const rowActions = computed(() => isInterviewer.value
  ? [
      { name: 'confirm', label: '确认', visible: (row) => row.status === 0 },
      { name: 'finish', label: '完成', type: 'success', visible: (row) => row.status === 1 }
    ]
  : [
      { name: 'cancel', label: '取消', type: 'danger', visible: (row) => row.status === 0 || row.status === 1 }
    ])

const handleAction = async (name, row) => {
  if (name === 'confirm') await confirmInterview(row.id)
  if (name === 'finish') await finishInterview(row.id)
  if (name === 'cancel') await cancelInterview(row.id)
  ElMessage.success('操作成功')
  pageRef.value.loadData()
}
</script>
