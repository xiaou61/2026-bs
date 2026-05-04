<template>
  <DataPage ref="pageRef" title="匹配结果" description="查看匹配技能、缺失技能、匹配分和推荐等级。" :api="api" :filters="filters" :columns="columns" :row-actions="rowActions" readonly @row-action="handleAction" />
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getMatchResultPage, reviewMatchResult } from '../api'

const pageRef = ref()
const reviewMap = { 0: { label: '待复核', type: 'warning' }, 1: { label: '通过', type: 'success' }, 2: { label: '驳回', type: 'danger' } }
const api = { page: getMatchResultPage }
const reviewOptions = [{ label: '待复核', value: 0 }, { label: '通过', value: 1 }, { label: '驳回', value: 2 }]
const filters = [{ type: 'input', prop: 'candidateId', label: '候选人ID' }, { type: 'select', prop: 'reviewStatus', label: '复核状态', options: reviewOptions }]
const columns = [{ prop: 'candidateId', label: '候选人ID' }, { prop: 'jobId', label: '岗位ID' }, { prop: 'recommendLevel', label: '推荐等级' }, { prop: 'matchScore', label: '分数' }, { prop: 'conclusion', label: '结论', minWidth: 260, long: true }, { prop: 'reviewStatus', label: '状态', map: reviewMap }]
const rowActions = [{ name: 'pass', label: '通过', type: 'success' }, { name: 'reject', label: '驳回', type: 'danger' }]

const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入复核意见', '结果复核', { inputValue: name === 'pass' ? '复核通过' : '复核驳回', confirmButtonText: '确定', cancelButtonText: '取消' })
  await reviewMatchResult({ id: row.id, reviewStatus: name === 'pass' ? 1 : 2, reviewComment: value })
  ElMessage.success('复核成功')
  pageRef.value.loadData()
}
</script>
