<template>
  <DataPage ref="pageRef" title="解析任务" description="为简历创建解析任务并生成结构化解析结果。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 0, priority: '普通' }" @row-action="handleAction" />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addParseTask, deleteParseTask, finishParseTask, getParseTaskPage, rejectParseTask, runParseTask, updateParseTask } from '../api'

const pageRef = ref()
const statusMap = { 0: { label: '待处理', type: 'warning' }, 1: { label: '已执行', type: 'primary' }, 2: { label: '已完成', type: 'success' }, 3: { label: '已驳回', type: 'danger' } }
const priorityMap = { 低: { label: '低', type: 'info' }, 普通: { label: '普通', type: 'primary' }, 高: { label: '高', type: 'danger' } }
const api = { page: getParseTaskPage, add: addParseTask, update: updateParseTask, delete: deleteParseTask }
const statusOptions = [{ label: '待处理', value: 0 }, { label: '已执行', value: 1 }, { label: '已完成', value: 2 }, { label: '已驳回', value: 3 }]
const priorityOptions = [{ label: '低', value: '低' }, { label: '普通', value: '普通' }, { label: '高', value: '高' }]
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'select', prop: 'status', label: '状态', options: statusOptions }, { type: 'select', prop: 'priority', label: '优先级', options: priorityOptions }]
const columns = [{ prop: 'taskNo', label: '任务号', minWidth: 160 }, { prop: 'taskName', label: '任务名称', minWidth: 180 }, { prop: 'resumeId', label: '简历ID' }, { prop: 'priority', label: '优先级', map: priorityMap }, { prop: 'status', label: '状态', map: statusMap }, { prop: 'createTime', label: '创建时间', minWidth: 170 }]
const formFields = [{ prop: 'taskName', label: '任务名称', required: true }, { prop: 'resumeId', label: '简历ID', type: 'number', min: 1, required: true },  { prop: 'priority', label: '优先级', type: 'select', options: priorityOptions }, { prop: 'status', label: '状态', type: 'select', options: statusOptions }]
const rowActions = [{ name: 'run', label: '启动' }, { name: 'finish', label: '完成', type: 'success' }, { name: 'reject', label: '驳回', type: 'danger' }]

const handleAction = async (name, row) => {
  if (name === 'run') await runParseTask(row.id)
  if (name === 'finish') await finishParseTask(row.id)
  if (name === 'reject') await rejectParseTask(row.id)
  ElMessage.success('操作成功')
  pageRef.value.loadData()
}
</script>
