<template>
  <DataPage
    ref="pageRef"
    title="申诉处理"
    description="对审核结果、版权登记和侵权线索发起申诉并记录处理结论。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="{ status: 0, targetType: '审核结果' }"
    @row-action="handleAction"
  />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addAppeal, deleteAppeal, getAppealPage, handleAppeal, updateAppeal } from '../api'

const pageRef = ref()
const statusMap = {
  0: { label: '待处理', type: 'warning' },
  1: { label: '已通过', type: 'success' },
  2: { label: '已驳回', type: 'danger' }
}
const api = { page: getAppealPage, add: addAppeal, update: updateAppeal, delete: deleteAppeal }
const targetOptions = [
  { label: '审核结果', value: '审核结果' },
  { label: '版权登记', value: '版权登记' },
  { label: '侵权线索', value: '侵权线索' }
]
const statusOptions = [
  { label: '待处理', value: 0 },
  { label: '已通过', value: 1 },
  { label: '已驳回', value: 2 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '原因/意见' },
  { type: 'select', prop: 'targetType', label: '对象类型', options: targetOptions },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'targetType', label: '对象类型' },
  { prop: 'targetId', label: '对象ID' },
  { prop: 'userId', label: '申诉人ID' },
  { prop: 'reason', label: '申诉理由', minWidth: 260, long: true },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'handleComment', label: '处理意见', minWidth: 220, long: true },
  { prop: 'createTime', label: '提交时间', minWidth: 170 }
]
const formFields = [
  { prop: 'targetType', label: '对象类型', type: 'select', options: targetOptions, required: true },
  { prop: 'targetId', label: '对象ID', type: 'number', min: 1, required: true },
  { prop: 'reason', label: '申诉理由', type: 'textarea', rows: 4, required: true },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true },
  { prop: 'handleComment', label: '处理意见', type: 'textarea', rows: 3 }
]
const rowActions = [
  { name: 'approve', label: '通过', type: 'success' },
  { name: 'reject', label: '驳回', type: 'danger' }
]

const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入处理意见', '申诉处理', {
    inputValue: name === 'approve' ? '申诉通过' : '申诉驳回',
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  })
  await handleAppeal({ id: row.id, status: name === 'approve' ? 1 : 2, handleComment: value })
  ElMessage.success('处理成功')
  pageRef.value.loadData()
}
</script>
