<template>
  <DataPage
    ref="pageRef"
    title="诚信预警"
    description="跟踪高风险文本预警、处理意见和整改要求。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :row-actions="rowActions"
    readonly
    @row-action="handleAction"
  />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { deleteWarning, getWarningPage, handleWarning } from '../api'

const pageRef = ref()
const levelMap = {
  低: { label: '低', type: 'success' },
  中: { label: '中', type: 'warning' },
  高: { label: '高', type: 'danger' }
}
const statusMap = {
  0: { label: '待处理', type: 'warning' },
  1: { label: '处理中', type: 'primary' },
  2: { label: '已关闭', type: 'success' }
}
const api = { page: getWarningPage, delete: deleteWarning }
const levelOptions = [
  { label: '低', value: '低' },
  { label: '中', value: '中' },
  { label: '高', value: '高' }
]
const statusOptions = [
  { label: '待处理', value: 0 },
  { label: '处理中', value: 1 },
  { label: '已关闭', value: 2 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '标题/内容/意见' },
  { type: 'input', prop: 'studentId', label: '学生ID' },
  { type: 'select', prop: 'warningLevel', label: '等级', options: levelOptions },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'warningTitle', label: '预警标题', minWidth: 180 },
  { prop: 'studentId', label: '学生ID' },
  { prop: 'warningLevel', label: '等级', map: levelMap },
  { prop: 'warningContent', label: '预警内容', minWidth: 260, long: true },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'handleComment', label: '处理意见', minWidth: 220, long: true }
]
const rowActions = [
  { name: 'processing', label: '处理' },
  { name: 'close', label: '关闭', type: 'success' }
]

const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入处理意见', '预警处理', {
    inputValue: name === 'processing' ? '已通知学生整改' : '预警已关闭',
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  })
  await handleWarning({ id: row.id, status: name === 'processing' ? 1 : 2, handleComment: value })
  ElMessage.success('处理成功')
  pageRef.value.loadData()
}
</script>
