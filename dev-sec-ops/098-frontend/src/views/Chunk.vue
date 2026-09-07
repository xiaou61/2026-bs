<template>
  <DataPage
    ref="pageRef"
    title="文档分段"
    description="维护文档分段内容、关键词、排序和模拟向量索引状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ vectorStatus: 0, sort: 0 }"
    :row-actions="rowActions"
    dialog-width="820px"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addChunk, deleteChunk, getChunkPage, getDocumentPage, indexChunk, updateChunk } from '../api'

const pageRef = ref()
const documents = ref([])
const vectorOptions = [
  { label: '未索引', value: 0 },
  { label: '已索引', value: 1 }
]
const vectorMap = {
  0: { label: '未索引', type: 'info' },
  1: { label: '已索引', type: 'success' }
}
const api = { page: getChunkPage, add: addChunk, update: updateChunk, delete: deleteChunk }
const documentOptions = computed(() => documents.value.map((item) => ({ label: item.title, value: item.id })))
const filters = computed(() => [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '标题/关键词/内容' },
  { prop: 'documentId', label: '文档', type: 'select', options: documentOptions.value },
  { prop: 'vectorStatus', label: '索引状态', type: 'select', options: vectorOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'documentId', label: '文档ID' },
  { prop: 'chunkTitle', label: '分段标题', minWidth: 180 },
  { prop: 'chunkContent', label: '分段内容', minWidth: 260, long: true },
  { prop: 'keywords', label: '关键词', minWidth: 160 },
  { prop: 'sort', label: '排序' },
  { prop: 'vectorStatus', label: '索引', map: vectorMap }
]
const formFields = computed(() => [
  { prop: 'documentId', label: '所属文档', type: 'select', required: true, options: documentOptions.value },
  { prop: 'chunkTitle', label: '分段标题', required: true },
  { prop: 'chunkContent', label: '分段内容', type: 'textarea', rows: 6, required: true },
  { prop: 'keywords', label: '关键词' },
  { prop: 'sort', label: '排序', type: 'number', min: 0 },
  { prop: 'vectorStatus', label: '索引状态', type: 'switch' }
])
const rowActions = [{ name: 'index', label: '生成索引', type: 'success' }]

const handleAction = async (name, row) => {
  if (name === 'index') {
    await indexChunk(row.id)
    ElMessage.success('索引已生成')
    pageRef.value.loadData()
  }
}

const loadOptions = async () => {
  const res = await getDocumentPage({ pageNum: 1, pageSize: 100, status: 1 })
  documents.value = res.data.list || []
}

onMounted(loadOptions)
</script>
