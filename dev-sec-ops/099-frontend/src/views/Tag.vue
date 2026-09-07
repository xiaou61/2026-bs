<template>
  <DataPage
    title="风险标签"
    description="维护审核结果中展示的内容、版权、肖像与商标标签。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, tagType: '内容' }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addTag, deleteTag, getTagPage, updateTag } from '../api'

const statusMap = {
  0: { label: '停用', type: 'info' },
  1: { label: '启用', type: 'success' }
}
const api = { page: getTagPage, add: addTag, update: updateTag, delete: deleteTag }
const typeOptions = [
  { label: '内容', value: '内容' },
  { label: '版权', value: '版权' },
  { label: '肖像', value: '肖像' },
  { label: '商标', value: '商标' }
]
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '标签名称/描述' },
  { type: 'select', prop: 'tagType', label: '类型', options: typeOptions },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'tagName', label: '标签名称' },
  { prop: 'tagType', label: '类型' },
  { prop: 'color', label: '颜色' },
  { prop: 'description', label: '描述', minWidth: 220, long: true },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'updateTime', label: '更新时间', minWidth: 170 }
]
const formFields = [
  { prop: 'tagName', label: '标签名称', required: true },
  { prop: 'tagType', label: '标签类型', type: 'select', options: typeOptions, required: true },
  { prop: 'color', label: '标签颜色', type: 'color' },
  { prop: 'description', label: '描述', type: 'textarea', rows: 3 },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
</script>
