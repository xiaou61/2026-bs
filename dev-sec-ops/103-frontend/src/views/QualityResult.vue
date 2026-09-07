<template>
  <DataPage ref="pageRef" title="质检结果" description="查看质检分数、风险等级、命中规则和复核意见。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="{}" @row-action="handleAction" />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getQualityResultPage, reviewQualityResult } from '../api'

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

const api = { page: getQualityResultPage }
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'input', prop: 'orderId', label: '工单ID' }, { type: 'select', prop: 'reviewStatus', label: '复核状态', options: reviewOptions }]
const columns = [{ prop: 'orderId', label: '工单ID' }, { prop: 'agentId', label: '坐席ID' }, { prop: 'score', label: '分数' }, { prop: 'riskLevel', label: '风险' }, { prop: 'hitRules', label: '命中规则', minWidth: 220, long: true }, { prop: 'reviewStatus', label: '复核', map: reviewMap }]
const formFields = []
const rowActions = [{ name: 'pass', label: '通过', type: 'success' }, { name: 'reject', label: '驳回', type: 'danger' }]
const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入复核意见', '质检复核', { inputValue: name === 'pass' ? '复核通过' : '复核驳回', confirmButtonText: '确定', cancelButtonText: '取消' })
  await reviewQualityResult({ id: row.id, reviewStatus: name === 'pass' ? 1 : 2, reviewComment: value })
  ElMessage.success('操作成功')
  pageRef.value.loadData()
}
</script>
