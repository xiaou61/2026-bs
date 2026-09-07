<template>
  <DataPage
    title="测试用例"
    description="维护 Prompt 评测输入、期望输出、评分要点和难度。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, difficulty: '中' }"
    dialog-width="780px"
  />
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import DataPage from '../components/DataPage.vue'
import { addCase, deleteCase, getAssetPage, getCasePage, updateCase } from '../api'

const assets = ref([])
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const difficultyOptions = [
  { label: '低', value: '低' },
  { label: '中', value: '中' },
  { label: '高', value: '高' }
]
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '停用', type: 'danger' }
}
const difficultyMap = {
  低: { label: '低', type: 'success' },
  中: { label: '中', type: 'warning' },
  高: { label: '高', type: 'danger' }
}
const api = { page: getCasePage, add: addCase, update: updateCase, delete: deleteCase }
const assetOptions = computed(() => assets.value.map((item) => ({ label: item.title, value: item.id })))
const filters = computed(() => [
  { prop: 'assetId', label: '资产', type: 'select', options: assetOptions.value },
  { prop: 'difficulty', label: '难度', type: 'select', options: difficultyOptions },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'assetId', label: '资产ID' },
  { prop: 'title', label: '用例标题', minWidth: 180 },
  { prop: 'inputText', label: '输入', minWidth: 220, long: true },
  { prop: 'scorePoints', label: '评分要点', minWidth: 220, long: true },
  { prop: 'difficulty', label: '难度', map: difficultyMap },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = computed(() => [
  { prop: 'assetId', label: '所属资产', type: 'select', required: true, options: assetOptions.value },
  { prop: 'title', label: '用例标题', required: true },
  { prop: 'inputText', label: '输入文本', type: 'textarea', rows: 4, required: true },
  { prop: 'expectedOutput', label: '期望输出', type: 'textarea', rows: 4 },
  { prop: 'scorePoints', label: '评分要点', type: 'textarea' },
  { prop: 'difficulty', label: '难度', type: 'select', options: difficultyOptions },
  { prop: 'status', label: '启用状态', type: 'switch' }
])

const loadOptions = async () => {
  const res = await getAssetPage({ pageNum: 1, pageSize: 100, status: 1 })
  assets.value = res.data.records || []
}

onMounted(loadOptions)
</script>
