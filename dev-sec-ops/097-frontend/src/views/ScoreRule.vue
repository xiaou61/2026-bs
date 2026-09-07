<template>
  <DataPage
    title="评分规则"
    description="维护评测维度、权重和评分说明。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, weight: 1 }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addRule, deleteRule, getRulePage, updateRule } from '../api'

const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '停用', type: 'danger' }
}
const api = { page: getRulePage, add: addRule, update: updateRule, delete: deleteRule }
const filters = [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '名称或维度' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'name', label: '规则名称' },
  { prop: 'dimension', label: '维度' },
  { prop: 'weight', label: '权重' },
  { prop: 'description', label: '说明', minWidth: 240, long: true },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'name', label: '规则名称', required: true },
  { prop: 'dimension', label: '维度', required: true },
  { prop: 'weight', label: '权重', type: 'number', min: 0, max: 1, precision: 2 },
  { prop: 'description', label: '说明', type: 'textarea' },
  { prop: 'status', label: '启用状态', type: 'switch' }
]
</script>
