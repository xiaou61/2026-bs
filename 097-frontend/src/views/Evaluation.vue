<template>
  <DataPage
    ref="pageRef"
    title="评测任务"
    description="创建 Prompt 版本评测任务，启动后自动生成模拟评测结果并统计通过率。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 0 }"
    :row-actions="rowActions"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addEvaluation, cancelEvaluation, deleteEvaluation, finishEvaluation, getAssetPage, getEvaluationPage, getModelPage, getVersionPage, runEvaluation, updateEvaluation } from '../api'

const pageRef = ref()
const assets = ref([])
const versions = ref([])
const models = ref([])
const statusOptions = [
  { label: '待评测', value: 0 },
  { label: '评测中', value: 1 },
  { label: '已完成', value: 2 },
  { label: '已取消', value: 3 }
]
const statusMap = {
  0: { label: '待评测', type: 'info' },
  1: { label: '评测中', type: 'warning' },
  2: { label: '已完成', type: 'success' },
  3: { label: '已取消', type: 'danger' }
}
const api = { page: getEvaluationPage, add: addEvaluation, update: updateEvaluation, delete: deleteEvaluation }
const assetOptions = computed(() => assets.value.map((item) => ({ label: item.title, value: item.id })))
const versionOptions = computed(() => versions.value.map((item) => ({ label: `${item.assetId} - ${item.versionNo}`, value: item.id })))
const modelOptions = computed(() => models.value.map((item) => ({ label: item.name, value: item.id })))
const filters = [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '任务名称' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'taskNo', label: '任务编号', minWidth: 180 },
  { prop: 'name', label: '任务名称', minWidth: 180 },
  { prop: 'assetId', label: '资产ID' },
  { prop: 'versionId', label: '版本ID' },
  { prop: 'modelId', label: '模型ID' },
  { prop: 'averageScore', label: '平均分' },
  { prop: 'passRate', label: '通过率' },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = computed(() => [
  { prop: 'name', label: '任务名称', required: true },
  { prop: 'assetId', label: 'Prompt资产', type: 'select', required: true, options: assetOptions.value },
  { prop: 'versionId', label: 'Prompt版本', type: 'select', required: true, options: versionOptions.value },
  { prop: 'modelId', label: '评测模型', type: 'select', required: true, options: modelOptions.value }
])
const rowActions = [
  { name: 'run', label: '启动', type: 'success' },
  { name: 'finish', label: '完成', type: 'primary' },
  { name: 'cancel', label: '取消', type: 'warning' }
]

const handleAction = async (name, row) => {
  if (name === 'run') {
    await runEvaluation(row.id)
    ElMessage.success('评测已启动')
  }
  if (name === 'finish') {
    await finishEvaluation(row.id)
    ElMessage.success('任务已完成')
  }
  if (name === 'cancel') {
    await cancelEvaluation(row.id)
    ElMessage.success('任务已取消')
  }
  pageRef.value.loadData()
}

const loadOptions = async () => {
  const [assetRes, versionRes, modelRes] = await Promise.all([
    getAssetPage({ pageNum: 1, pageSize: 100, status: 1 }),
    getVersionPage({ pageNum: 1, pageSize: 100, status: 1 }),
    getModelPage({ pageNum: 1, pageSize: 100, status: 1 })
  ])
  assets.value = assetRes.data.records || []
  versions.value = versionRes.data.records || []
  models.value = modelRes.data.records || []
}

onMounted(loadOptions)
</script>
