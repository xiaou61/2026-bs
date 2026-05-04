<template>
  <DataPage
    title="检测规则"
    description="配置AI特征、重复片段、引用规范和异常风格规则。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, riskLevel: '中', ruleType: 'AI特征', weight: 1 }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addRule, deleteRule, getRulePage, updateRule } from '../api'

const riskMap = {
  低: { label: '低', type: 'success' },
  中: { label: '中', type: 'warning' },
  高: { label: '高', type: 'danger' }
}
const statusMap = {
  0: { label: '停用', type: 'info' },
  1: { label: '启用', type: 'success' }
}
const api = { page: getRulePage, add: addRule, update: updateRule, delete: deleteRule }
const typeOptions = [
  { label: 'AI特征', value: 'AI特征' },
  { label: '重复片段', value: '重复片段' },
  { label: '引用规范', value: '引用规范' },
  { label: '异常风格', value: '异常风格' }
]
const riskOptions = [
  { label: '低', value: '低' },
  { label: '中', value: '中' },
  { label: '高', value: '高' }
]
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '规则名称/关键词' },
  { type: 'select', prop: 'ruleType', label: '类型', options: typeOptions },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'ruleName', label: '规则名称', minWidth: 180 },
  { prop: 'ruleType', label: '规则类型' },
  { prop: 'riskLevel', label: '风险', map: riskMap },
  { prop: 'weight', label: '权重' },
  { prop: 'keywords', label: '关键词', minWidth: 240, long: true },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'ruleName', label: '规则名称', required: true },
  { prop: 'ruleType', label: '规则类型', type: 'select', options: typeOptions, required: true },
  { prop: 'riskLevel', label: '风险等级', type: 'select', options: riskOptions, required: true },
  { prop: 'weight', label: '权重', type: 'number', min: 0, max: 5, precision: 2 },
  { prop: 'keywords', label: '匹配关键词', type: 'textarea', rows: 3, required: true },
  { prop: 'description', label: '说明', type: 'textarea', rows: 3 },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
</script>
