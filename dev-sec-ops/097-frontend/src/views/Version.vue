<template>
  <DataPage
    ref="pageRef"
    title="Prompt 版本"
    description="管理 Prompt 内容、变量、适用模型、基准版本和发布状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 0, isBaseline: 0 }"
    :row-actions="rowActions"
    dialog-width="820px"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addVersion, deleteVersion, getAssetPage, getVersionPage, publishVersion, updateVersion } from '../api'

const pageRef = ref()
const assets = ref([])
const statusOptions = [
  { label: '草稿', value: 0 },
  { label: '已发布', value: 1 }
]
const statusMap = {
  0: { label: '草稿', type: 'info' },
  1: { label: '已发布', type: 'success' }
}
const baselineMap = {
  1: { label: '基准', type: 'warning' },
  0: { label: '普通', type: 'info' }
}
const api = { page: getVersionPage, add: addVersion, update: updateVersion, delete: deleteVersion }
const assetOptions = computed(() => assets.value.map((item) => ({ label: item.title, value: item.id })))
const filters = computed(() => [
  { prop: 'assetId', label: '资产', type: 'select', options: assetOptions.value },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'assetId', label: '资产ID' },
  { prop: 'versionNo', label: '版本号' },
  { prop: 'modelHint', label: '适用模型' },
  { prop: 'content', label: '内容', minWidth: 260, long: true },
  { prop: 'isBaseline', label: '基准', map: baselineMap },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = computed(() => [
  { prop: 'assetId', label: '所属资产', type: 'select', required: true, options: assetOptions.value },
  { prop: 'versionNo', label: '版本号', required: true },
  { prop: 'content', label: 'Prompt 内容', type: 'textarea', rows: 6, required: true },
  { prop: 'variables', label: '变量说明', type: 'textarea' },
  { prop: 'modelHint', label: '适用模型' },
  { prop: 'changeLog', label: '变更说明', type: 'textarea' },
  { prop: 'isBaseline', label: '基准版本', type: 'switch' },
  { prop: 'status', label: '发布状态', type: 'switch' }
])
const rowActions = [{ name: 'publish', label: '发布', type: 'success' }]

const handleAction = async (name, row) => {
  if (name === 'publish') {
    await publishVersion(row.id)
    ElMessage.success('发布成功')
    pageRef.value.loadData()
  }
}

const loadOptions = async () => {
  const res = await getAssetPage({ pageNum: 1, pageSize: 100, status: 1 })
  assets.value = res.data.records || []
}

onMounted(loadOptions)
</script>
