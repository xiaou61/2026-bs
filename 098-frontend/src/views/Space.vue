<template>
  <DataPage title="知识空间" description="维护企业知识库空间、负责人、可见级别和启用状态。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :defaults="{ status: 1, visibility: 'GROUP' }" />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addSpace, deleteSpace, getSpacePage, updateSpace } from '../api'

const visibilityOptions = [
  { label: '公开', value: 'PUBLIC' },
  { label: '权限组', value: 'GROUP' },
  { label: '私有', value: 'PRIVATE' }
]
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const visibilityMap = {
  PUBLIC: { label: '公开', type: 'success' },
  GROUP: { label: '权限组', type: 'warning' },
  PRIVATE: { label: '私有', type: 'info' }
}
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '停用', type: 'danger' }
}
const api = { page: getSpacePage, add: addSpace, update: updateSpace, delete: deleteSpace }
const filters = [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '空间/负责人' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'name', label: '空间名称', minWidth: 180 },
  { prop: 'ownerName', label: '负责人' },
  { prop: 'visibility', label: '可见级别', map: visibilityMap },
  { prop: 'description', label: '说明', minWidth: 260, long: true },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'name', label: '空间名称', required: true },
  { prop: 'ownerName', label: '负责人' },
  { prop: 'visibility', label: '可见级别', type: 'select', options: visibilityOptions },
  { prop: 'description', label: '说明', type: 'textarea' },
  { prop: 'status', label: '启用状态', type: 'switch' }
]
</script>
