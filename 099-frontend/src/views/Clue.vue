<template>
  <DataPage
    ref="pageRef"
    title="侵权线索"
    description="登记疑似侵权链接、证据材料和审核员处理意见。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="{ status: 0, platformName: '小红书' }"
    dialog-width="760px"
    @row-action="handleAction"
  />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addClue, deleteClue, getCluePage, handleClue, updateClue } from '../api'

const pageRef = ref()
const statusMap = {
  0: { label: '待处理', type: 'warning' },
  1: { label: '处理中', type: 'primary' },
  2: { label: '已关闭', type: 'success' }
}
const api = { page: getCluePage, add: addClue, update: updateClue, delete: deleteClue }
const statusOptions = [
  { label: '待处理', value: 0 },
  { label: '处理中', value: 1 },
  { label: '已关闭', value: 2 }
]
const platformOptions = [
  { label: '小红书', value: '小红书' },
  { label: '抖音', value: '抖音' },
  { label: '淘宝', value: '淘宝' },
  { label: '公众号', value: '公众号' }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '平台/描述' },
  { type: 'input', prop: 'assetId', label: '作品ID' },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'assetId', label: '作品ID' },
  { prop: 'platformName', label: '平台' },
  { prop: 'clueUrl', label: '线索链接', minWidth: 220, long: true },
  { prop: 'description', label: '线索描述', minWidth: 220, long: true },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'handlerId', label: '处理人ID' },
  { prop: 'handleComment', label: '处理意见', minWidth: 180, long: true }
]
const formFields = [
  { prop: 'assetId', label: '作品ID', type: 'number', min: 1, required: true },
  { prop: 'platformName', label: '平台', type: 'select', options: platformOptions, required: true },
  { prop: 'clueUrl', label: '线索链接', required: true },
  { prop: 'description', label: '线索描述', type: 'textarea', rows: 3, required: true },
  { prop: 'evidenceUrl', label: '证据地址' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true },
  { prop: 'handleComment', label: '处理意见', type: 'textarea', rows: 3 }
]
const rowActions = [
  { name: 'processing', label: '处理中' },
  { name: 'close', label: '关闭', type: 'success' }
]

const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入处理意见', '线索处理', {
    inputValue: name === 'processing' ? '已进入核查处理' : '线索已闭环',
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  })
  await handleClue({ id: row.id, status: name === 'processing' ? 1 : 2, handleComment: value })
  ElMessage.success('处理成功')
  pageRef.value.loadData()
}
</script>
