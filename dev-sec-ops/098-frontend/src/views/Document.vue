<template>
  <DataPage
    ref="pageRef"
    title="知识文档"
    description="维护文档标题、摘要、来源、标签、正文和发布状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 0, sourceType: '手工录入' }"
    :row-actions="rowActions"
    dialog-width="860px"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addDocument, archiveDocument, deleteDocument, getCategoryPage, getDocumentPage, getSpacePage, publishDocument, updateDocument } from '../api'

const pageRef = ref()
const spaces = ref([])
const categories = ref([])
const statusOptions = [
  { label: '草稿', value: 0 },
  { label: '已发布', value: 1 },
  { label: '已归档', value: 2 }
]
const statusMap = {
  0: { label: '草稿', type: 'info' },
  1: { label: '已发布', type: 'success' },
  2: { label: '已归档', type: 'warning' }
}
const sourceOptions = [
  { label: '手工录入', value: '手工录入' },
  { label: '导入文档', value: '导入文档' },
  { label: '网页采集', value: '网页采集' }
]
const api = { page: getDocumentPage, add: addDocument, update: updateDocument, delete: deleteDocument }
const spaceOptions = computed(() => spaces.value.map((item) => ({ label: item.name, value: item.id })))
const categoryOptions = computed(() => categories.value.map((item) => ({ label: item.name, value: item.id })))
const filters = computed(() => [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '标题/摘要/标签' },
  { prop: 'spaceId', label: '空间', type: 'select', options: spaceOptions.value },
  { prop: 'categoryId', label: '分类', type: 'select', options: categoryOptions.value },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'spaceId', label: '空间ID' },
  { prop: 'categoryId', label: '分类ID' },
  { prop: 'title', label: '文档标题', minWidth: 190 },
  { prop: 'summary', label: '摘要', minWidth: 240, long: true },
  { prop: 'sourceType', label: '来源' },
  { prop: 'tags', label: '标签' },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = computed(() => [
  { prop: 'spaceId', label: '知识空间', type: 'select', required: true, options: spaceOptions.value },
  { prop: 'categoryId', label: '文档分类', type: 'select', required: true, options: categoryOptions.value },
  { prop: 'title', label: '文档标题', required: true },
  { prop: 'summary', label: '摘要', type: 'textarea', rows: 3 },
  { prop: 'sourceType', label: '来源类型', type: 'select', options: sourceOptions },
  { prop: 'tags', label: '标签' },
  { prop: 'content', label: '正文内容', type: 'textarea', rows: 7 },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
])
const rowActions = [
  { name: 'publish', label: '发布', type: 'success' },
  { name: 'archive', label: '归档', type: 'warning' }
]

const handleAction = async (name, row) => {
  if (name === 'publish') {
    await publishDocument(row.id)
    ElMessage.success('发布成功')
  }
  if (name === 'archive') {
    await archiveDocument(row.id)
    ElMessage.success('归档成功')
  }
  pageRef.value.loadData()
}

const loadOptions = async () => {
  const [spaceRes, categoryRes] = await Promise.all([
    getSpacePage({ pageNum: 1, pageSize: 100, status: 1 }),
    getCategoryPage({ pageNum: 1, pageSize: 100, status: 1 })
  ])
  spaces.value = spaceRes.data.list || []
  categories.value = categoryRes.data.list || []
}

onMounted(loadOptions)
</script>
