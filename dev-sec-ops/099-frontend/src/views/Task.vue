<template>
  <DataPage
    ref="pageRef"
    title="审核任务"
    description="创建审核任务并触发规则匹配，形成风险等级和审核建议。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="{ status: 0, priority: '普通' }"
    @row-action="handleAction"
  />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addTask, deleteTask, finishTask, getTaskPage, rejectTask, runTask, updateTask } from '../api'

const pageRef = ref()
const statusMap = {
  0: { label: '待审核', type: 'warning' },
  1: { label: '已初审', type: 'primary' },
  2: { label: '已完成', type: 'success' },
  3: { label: '已退回', type: 'danger' }
}
const priorityMap = {
  低: { label: '低', type: 'info' },
  普通: { label: '普通', type: 'primary' },
  高: { label: '高', type: 'danger' }
}
const api = { page: getTaskPage, add: addTask, update: updateTask, delete: deleteTask }
const statusOptions = [
  { label: '待审核', value: 0 },
  { label: '已初审', value: 1 },
  { label: '已完成', value: 2 },
  { label: '已退回', value: 3 }
]
const priorityOptions = [
  { label: '低', value: '低' },
  { label: '普通', value: '普通' },
  { label: '高', value: '高' }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '任务号/任务名' },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions },
  { type: 'select', prop: 'priority', label: '优先级', options: priorityOptions }
]
const columns = [
  { prop: 'taskNo', label: '任务号', minWidth: 160 },
  { prop: 'taskName', label: '任务名称', minWidth: 180 },
  { prop: 'assetId', label: '作品ID' },
  { prop: 'priority', label: '优先级', map: priorityMap },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'auditorId', label: '审核员ID' },
  { prop: 'createTime', label: '创建时间', minWidth: 170 }
]
const formFields = [
  { prop: 'taskName', label: '任务名称', required: true },
  { prop: 'assetId', label: '作品ID', type: 'number', min: 1, required: true },
  { prop: 'priority', label: '优先级', type: 'select', options: priorityOptions, required: true },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
const rowActions = [
  { name: 'run', label: '执行' },
  { name: 'finish', label: '完成', type: 'success' },
  { name: 'reject', label: '退回', type: 'danger' }
]

const handleAction = async (name, row) => {
  if (name === 'run') await runTask(row.id)
  if (name === 'finish') await finishTask(row.id)
  if (name === 'reject') await rejectTask(row.id)
  ElMessage.success('操作成功')
  pageRef.value.loadData()
}
</script>
