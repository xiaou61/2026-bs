<template>
  <DataPage
    ref="pageRef"
    title="整改跟踪"
    description="学生提交整改说明和修订稿，教师或复核员审核整改状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="{ status: 0 }"
    @row-action="handleAction"
  />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addRectification, deleteRectification, getRectificationPage, reviewRectification, updateRectification } from '../api'

const pageRef = ref()
const statusMap = {
  0: { label: '待审核', type: 'warning' },
  1: { label: '整改通过', type: 'success' },
  2: { label: '继续整改', type: 'danger' }
}
const api = { page: getRectificationPage, add: addRectification, update: updateRectification, delete: deleteRectification }
const statusOptions = [
  { label: '待审核', value: 0 },
  { label: '整改通过', value: 1 },
  { label: '继续整改', value: 2 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '整改说明/审核意见' },
  { type: 'input', prop: 'warningId', label: '预警ID' },
  { type: 'input', prop: 'studentId', label: '学生ID' },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'warningId', label: '预警ID' },
  { prop: 'studentId', label: '学生ID' },
  { prop: 'revisionNote', label: '整改说明', minWidth: 260, long: true },
  { prop: 'revisionUrl', label: '修订稿', minWidth: 180, long: true },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'reviewComment', label: '审核意见', minWidth: 220, long: true }
]
const formFields = [
  { prop: 'warningId', label: '预警ID', type: 'number', min: 1, required: true },
  { prop: 'studentId', label: '学生ID', type: 'number', min: 1, required: true },
  { prop: 'revisionNote', label: '整改说明', type: 'textarea', rows: 4, required: true },
  { prop: 'revisionUrl', label: '修订稿地址' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true },
  { prop: 'reviewComment', label: '审核意见', type: 'textarea', rows: 3 }
]
const rowActions = [
  { name: 'pass', label: '通过', type: 'success' },
  { name: 'reject', label: '继续整改', type: 'danger' }
]

const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入审核意见', '整改审核', {
    inputValue: name === 'pass' ? '整改通过' : '需要继续整改',
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  })
  await reviewRectification({ id: row.id, status: name === 'pass' ? 1 : 2, reviewComment: value })
  ElMessage.success('审核成功')
  pageRef.value.loadData()
}
</script>
