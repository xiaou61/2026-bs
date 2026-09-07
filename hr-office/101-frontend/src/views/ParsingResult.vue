<template>
  <DataPage ref="pageRef" title="解析结果" description="查看结构化提取字段、解析分和人工复核意见。" :api="api" :filters="filters" :columns="columns" :row-actions="rowActions" readonly @row-action="handleAction" />
</template>

<script setup>
import { computed, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { getParseResultPage, reviewParseResult } from '../api'

const pageRef = ref()
const reviewMap = { 0: { label: '待复核', type: 'warning' }, 1: { label: '通过', type: 'success' }, 2: { label: '驳回', type: 'danger' } }
const api = { page: getParseResultPage }
const reviewOptions = [{ label: '待复核', value: 0 }, { label: '通过', value: 1 }, { label: '驳回', value: 2 }]
const filters = [{ type: 'input', prop: 'candidateId', label: '候选人ID' }, { type: 'select', prop: 'reviewStatus', label: '复核状态', options: reviewOptions }]
const columns = [{ prop: 'candidateId', label: '候选人ID' }, { prop: 'resumeId', label: '简历ID' },  { prop: 'score', label: '分数' }, { prop: 'conclusion', label: '结论', minWidth: 260, long: true }, { prop: 'reviewStatus', label: '状态', map: reviewMap }]
const rowActions = computed(() => [
  { name: 'pass', label: '通过', type: 'success', visible: (row) => row.reviewStatus === 0 },
  { name: 'reject', label: '驳回', type: 'danger', visible: (row) => row.reviewStatus === 0 }
])

const handleAction = async (name, row) => {
  const { value } = await ElMessageBox.prompt('请输入复核意见', '结果复核', { inputValue: name === 'pass' ? '复核通过' : '复核驳回', confirmButtonText: '确定', cancelButtonText: '取消' })
  await reviewParseResult({ id: row.id, reviewStatus: name === 'pass' ? 1 : 2, reviewComment: value })
  ElMessage.success('复核成功')
  pageRef.value.loadData()
}
</script>
