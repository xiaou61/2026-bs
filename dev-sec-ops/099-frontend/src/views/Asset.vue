<template>
  <DataPage
    title="图片作品"
    description="登记生成图片、模型来源、提示词和当前审核状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 0, category: '商业海报', modelName: 'Stable Diffusion' }"
    dialog-width="760px"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addAsset, deleteAsset, getAssetPage, updateAsset } from '../api'

const statusMap = {
  0: { label: '待审核', type: 'warning' },
  1: { label: '已通过', type: 'success' },
  2: { label: '已登记', type: 'primary' },
  3: { label: '高风险', type: 'danger' }
}

const api = { page: getAssetPage, add: addAsset, update: updateAsset, delete: deleteAsset }
const statusOptions = [
  { label: '待审核', value: 0 },
  { label: '已通过', value: 1 },
  { label: '已登记', value: 2 },
  { label: '高风险', value: 3 }
]
const categoryOptions = [
  { label: '商业海报', value: '商业海报' },
  { label: '角色设定', value: '角色设定' },
  { label: '电商素材', value: '电商素材' },
  { label: '课程插画', value: '课程插画' }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '标题/提示词/描述' },
  { type: 'select', prop: 'category', label: '分类', options: categoryOptions },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'title', label: '作品标题', minWidth: 180 },
  { prop: 'category', label: '分类' },
  { prop: 'modelName', label: '生成模型', minWidth: 150 },
  { prop: 'promptText', label: '提示词', minWidth: 220, long: true },
  { prop: 'creatorId', label: '创作者ID' },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'createTime', label: '创建时间', minWidth: 170 }
]
const formFields = [
  { prop: 'title', label: '作品标题', required: true },
  { prop: 'imageUrl', label: '图片地址', required: true },
  { prop: 'promptText', label: '提示词', type: 'textarea', rows: 3, required: true },
  { prop: 'modelName', label: '生成模型', required: true },
  { prop: 'category', label: '分类', type: 'select', options: categoryOptions, required: true },
  { prop: 'description', label: '作品描述', type: 'textarea', rows: 3 },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
</script>
