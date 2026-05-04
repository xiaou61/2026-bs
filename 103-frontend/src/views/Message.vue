<template>
  <DataPage ref="pageRef" title="会话消息" description="记录客户与坐席的工单沟通内容。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{ sensitiveFlag: 0 }" @row-action="handleAction" />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addOrderMessage, deleteOrderMessage, getOrderMessagePage, updateOrderMessage } from '../api'

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

const api = { page: getOrderMessagePage, add: addOrderMessage, update: updateOrderMessage, delete: deleteOrderMessage }
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'input', prop: 'orderId', label: '工单ID' }, { type: 'select', prop: 'sensitiveFlag', label: '敏感标记', options: flagOptions }]
const columns = [{ prop: 'orderId', label: '工单ID' }, { prop: 'senderType', label: '发送方' }, { prop: 'messageContent', label: '消息内容', minWidth: 260, long: true }, { prop: 'sensitiveFlag', label: '敏感', map: flagMap }, { prop: 'sendTime', label: '发送时间', minWidth: 170 }]
const formFields = [{ prop: 'orderId', label: '工单ID', type: 'number', min: 1, required: true }, { prop: 'senderId', label: '发送人ID', type: 'number', min: 1 }, { prop: 'senderType', label: '发送方' }, { prop: 'messageContent', label: '消息内容', type: 'textarea', rows: 5 }, { prop: 'sensitiveFlag', label: '敏感标记', type: 'select', options: flagOptions }, { prop: 'sendTime', label: '发送时间', type: 'datetime' }]
const rowActions = []
const handleAction = async (name, row) => {
  return name && row
}
</script>
