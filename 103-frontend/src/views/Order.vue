<template>
  <DataPage ref="pageRef" title="客服工单" description="管理客户工单并支持受理、解决和关闭。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ status: 0, priority: '普通' }" @row-action="handleAction" />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { acceptOrder, addWorkOrder, closeOrder, deleteWorkOrder, getWorkOrderPage, resolveOrder, updateWorkOrder } from '../api'

const pageRef = ref()

const statusMap = { 0: { label: '停用', type: 'info' }, 1: { label: '启用', type: 'success' }, 2: { label: '完成', type: 'primary' }, 3: { label: '关闭', type: 'danger' } }
const statusOptions = [{ label: '停用', value: 0 }, { label: '启用', value: 1 }, { label: '完成', value: 2 }, { label: '关闭', value: 3 }]
const roleMap = { ADMIN: { label: '管理员', type: 'danger' }, SUPERVISOR: { label: '主管', type: 'success' }, AGENT: { label: '坐席', type: 'primary' }, QA: { label: '质检员', type: 'warning' } }
const roleOptions = [{ label: '管理员', value: 'ADMIN' }, { label: '主管', value: 'SUPERVISOR' }, { label: '坐席', value: 'AGENT' }, { label: '质检员', value: 'QA' }]
const priorityMap = { 低: { label: '低', type: 'info' }, 普通: { label: '普通', type: 'primary' }, 高: { label: '高', type: 'danger' } }
const priorityOptions = [{ label: '低', value: '低' }, { label: '普通', value: '普通' }, { label: '高', value: '高' }]
const orderMap = { 0: { label: '待受理', type: 'warning' }, 1: { label: '处理中', type: 'primary' }, 2: { label: '已解决', type: 'success' }, 3: { label: '已关闭', type: 'info' } }
const orderOptions = [{ label: '待受理', value: 0 }, { label: '处理中', value: 1 }, { label: '已解决', value: 2 }, { label: '已关闭', value: 3 }]
const reviewMap = { 0: { label: '待复核', type: 'warning' }, 1: { label: '通过', type: 'success' }, 2: { label: '驳回', type: 'danger' } }
const reviewOptions = [{ label: '待复核', value: 0 }, { label: '通过', value: 1 }, { label: '驳回', value: 2 }]
const adoptMap = { 0: { label: '未处理', type: 'warning' }, 1: { label: '已采纳', type: 'success' }, 2: { label: '未采纳', type: 'danger' } }
const adoptOptions = [{ label: '未处理', value: 0 }, { label: '已采纳', value: 1 }, { label: '未采纳', value: 2 }]
const flagMap = { 0: { label: '正常', type: 'success' }, 1: { label: '敏感', type: 'danger' } }
const flagOptions = [{ label: '正常', value: 0 }, { label: '敏感', value: 1 }]

const api = { page: getWorkOrderPage, add: addWorkOrder, update: updateWorkOrder, delete: deleteWorkOrder }
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'select', prop: 'status', label: '状态', options: orderOptions }, { type: 'select', prop: 'priority', label: '优先级', options: priorityOptions }]
const columns = [{ prop: 'orderNo', label: '工单号', minWidth: 150 }, { prop: 'title', label: '标题', minWidth: 220 }, { prop: 'priority', label: '优先级', map: priorityMap }, { prop: 'status', label: '状态', map: orderMap }, { prop: 'solution', label: '解决方案', minWidth: 220, long: true }]
const formFields = [{ prop: 'customerId', label: '客户ID', type: 'number', min: 1, required: true }, { prop: 'agentId', label: '坐席ID', type: 'number', min: 1 }, { prop: 'channelId', label: '渠道ID', type: 'number', min: 1 }, { prop: 'title', label: '标题', required: true }, { prop: 'content', label: '内容', type: 'textarea', rows: 4 }, { prop: 'priority', label: '优先级', type: 'select', options: priorityOptions }, { prop: 'status', label: '状态', type: 'select', options: orderOptions }, { prop: 'solution', label: '解决方案', type: 'textarea', rows: 3 }]
const rowActions = [{ name: 'accept', label: '受理' }, { name: 'resolve', label: '解决', type: 'success' }, { name: 'close', label: '关闭', type: 'danger' }]
const handleAction = async (name, row) => {
  if (name === 'accept') await acceptOrder(row.id)
  if (name === 'resolve') await resolveOrder(row.id)
  if (name === 'close') await closeOrder(row.id)
  ElMessage.success('操作成功')
  pageRef.value.loadData()
}
</script>
