<template>
  <DataPage title="文档分类" description="按知识空间维护文档分类、编码和排序。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :defaults="{ status: 1, sort: 0 }" />
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import DataPage from '../components/DataPage.vue'
import { addCategory, deleteCategory, getCategoryPage, getSpacePage, updateCategory } from '../api'

const spaces = ref([])
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '停用', type: 'danger' }
}
const api = { page: getCategoryPage, add: addCategory, update: updateCategory, delete: deleteCategory }
const spaceOptions = computed(() => spaces.value.map((item) => ({ label: item.name, value: item.id })))
const filters = computed(() => [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '名称或编码' },
  { prop: 'spaceId', label: '空间', type: 'select', options: spaceOptions.value },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'spaceId', label: '空间ID' },
  { prop: 'name', label: '分类名称' },
  { prop: 'code', label: '分类编码', minWidth: 160 },
  { prop: 'sort', label: '排序' },
  { prop: 'description', label: '说明', minWidth: 220, long: true },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = computed(() => [
  { prop: 'spaceId', label: '知识空间', type: 'select', required: true, options: spaceOptions.value },
  { prop: 'name', label: '分类名称', required: true },
  { prop: 'code', label: '分类编码', required: true },
  { prop: 'sort', label: '排序', type: 'number', min: 0 },
  { prop: 'description', label: '说明', type: 'textarea' },
  { prop: 'status', label: '启用状态', type: 'switch' }
])

const loadOptions = async () => {
  const res = await getSpacePage({ pageNum: 1, pageSize: 100, status: 1 })
  spaces.value = res.data.list || []
}

onMounted(loadOptions)
</script>
