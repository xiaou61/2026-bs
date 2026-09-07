<template>
  <DataPage
    title="模型配置"
    description="维护评测使用的模型供应商、模型名称、温度和最大 token。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, isDefault: 0, temperature: 0.7, maxTokens: 2048 }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addModel, deleteModel, getModelPage, updateModel } from '../api'

const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '停用', type: 'danger' }
}
const defaultMap = {
  1: { label: '默认', type: 'success' },
  0: { label: '普通', type: 'info' }
}
const api = { page: getModelPage, add: addModel, update: updateModel, delete: deleteModel }
const filters = [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '名称/供应商/模型' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'name', label: '配置名称' },
  { prop: 'provider', label: '供应商' },
  { prop: 'modelName', label: '模型名称', minWidth: 160 },
  { prop: 'temperature', label: '温度' },
  { prop: 'maxTokens', label: '最大Token' },
  { prop: 'isDefault', label: '默认', map: defaultMap },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'name', label: '配置名称', required: true },
  { prop: 'provider', label: '供应商' },
  { prop: 'modelName', label: '模型名称', required: true },
  { prop: 'temperature', label: '温度', type: 'number', min: 0, max: 2, precision: 2 },
  { prop: 'maxTokens', label: '最大Token', type: 'number', min: 256 },
  { prop: 'isDefault', label: '默认模型', type: 'switch' },
  { prop: 'status', label: '启用状态', type: 'switch' }
]
</script>
