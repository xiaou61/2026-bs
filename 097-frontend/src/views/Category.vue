<template>
  <DataPage
    title="Prompt 分类"
    description="管理提示词资产分类、编码、排序和启停状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, sort: 0 }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addCategory, deleteCategory, getCategoryPage, updateCategory } from '../api'

const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '停用', type: 'danger' }
}
const api = { page: getCategoryPage, add: addCategory, update: updateCategory, delete: deleteCategory }
const filters = [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '名称或编码' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'name', label: '分类名称' },
  { prop: 'code', label: '分类编码', minWidth: 160 },
  { prop: 'sort', label: '排序' },
  { prop: 'description', label: '说明', minWidth: 220, long: true },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'name', label: '分类名称', required: true },
  { prop: 'code', label: '分类编码', required: true },
  { prop: 'sort', label: '排序', type: 'number', min: 0 },
  { prop: 'description', label: '说明', type: 'textarea' },
  { prop: 'status', label: '启用状态', type: 'switch' }
]
</script>
