<template>
  <DataPage
    ref="pageRef"
    title="审核结果"
    description="查看规则命中、风险等级、评分、结论并完成人工复核。"
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
  { prop: 'assetId', label: '作品ID' },
  { prop: 'matchedRules', label: '命中规则', minWidth: 220, long: true },
  { prop: 'riskLevel', label: '风险等级', map: riskMap },
  { prop: 'score', label: '评分' },
  { prop: 'conclusion', label: '结论', minWidth: 180 },
  { prop: 'suggestion', label: '建议', minWidth: 220, long: true },
  { prop: 'reviewStatus', label: '复核状态', map: reviewMap }
]
const rowActions = [
  { name: 'pass', label: '通过', type: 'success' },
  { name: 'reject', label: '驳回', type: 'danger' }
]

const handleAction = async (name, row) => {
  const defaultText = name === 'pass' ? '人工复核通过' : '人工复核驳回'
  const { value } = await ElMessageBox.prompt('请输入复核意见', '复核处理', {
    inputValue: defaultText,
    confirmButtonText: '确定',
    cancelButtonText: '取消'
  })
  await reviewResult({ id: row.id, reviewStatus: name === 'pass' ? 1 : 2, reviewComment: value })
  ElMessage.success('复核成功')
  pageRef.value.loadData()
}
</script>
