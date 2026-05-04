<template>
  <DataPage
    ref="pageRef"
    title="检测结果"
    description="查看AI生成概率、重复率、引用风险、综合分数和人工复核状态。"
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
import { getResultPage, reviewResult } from '../api'

const pageRef = ref()
const riskMap = {
  低: { label: '低风险', type: 'success' },
  中: { label: '中风险', type: 'warning' },
  高: { label: '高风险', type: 'danger' }
}
const reviewMap = {
  0: { label: '待复核', type: 'warning' },
  1: { label: '复核通过', type: 'success' },
  2: { label: '复核驳回', type: 'danger' }
}
const api = { page: getResultPage }
const riskOptions = [
  { label: '低', value: '低' },
  { label: '中', value: '中' },
  { label: '高', value: '高' }
]
const reviewOptions = [
  { label: '待复核', value: 0 },
  { label: '复核通过', value: 1 },
  { label: '复核驳回', value: 2 }
]
const filters = [
  { type: 'input', prop: 'taskId', label: '任务ID' },
  { type: 'select', prop: 'riskLevel', label: '风险等级', options: riskOptions },
  { type: 'select', prop: 'reviewStatus', label: '复核状态', options: reviewOptions }
]
const columns = [
  { prop: 'taskId', label: '任务ID' },
  { prop: 'submissionId', label: '提交ID' },
  { prop: 'matchedRules', label: '命中规则', minWidth: 220, long: true },
  { prop: 'aiProbability', label: 'AI概率' },
  { prop: 'repeatRate', label: '重复率' },
  { prop: 'citationRisk', label: '引用风险' },
  { prop: 'riskLevel', label: '风险等级', map: riskMap },
  { prop: 'score', label: '诚信分' },
  { prop: 'reviewStatus', label: '复核状态', map: reviewMap }
]
const rowActions = [
  { name: 'pass', label: '通过', type: 'success' },
  { name: 'reject', label: '驳回', type: 'danger' }
]

const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入复核意见', '人工复核', {
    inputValue: name === 'pass' ? '复核通过' : '复核驳回',
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  })
  await reviewResult({ id: row.id, reviewStatus: name === 'pass' ? 1 : 2, reviewComment: value })
  ElMessage.success('复核成功')
  pageRef.value.loadData()
}
</script>
