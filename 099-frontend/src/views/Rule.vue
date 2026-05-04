<template>
  <DataPage
    title="审核规则"
    description="配置敏感内容、版权相似、人物肖像和商标风险的审核规则。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, riskLevel: '中', ruleType: '内容安全' }"
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
  { label: '内容安全', value: '内容安全' },
  { label: '版权相似', value: '版权相似' },
  { label: '肖像风险', value: '肖像风险' },
  { label: '商标风险', value: '商标风险' }
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
  { type: 'select', prop: 'ruleType', label: '规则类型', options: typeOptions },
  { type: 'select', prop: 'status', label: '状态', options: statusOptions }
]
const columns = [
  { prop: 'ruleName', label: '规则名称', minWidth: 180 },
  { prop: 'ruleType', label: '规则类型' },
  { prop: 'riskLevel', label: '风险级别', map: riskMap },
  { prop: 'keywords', label: '关键词', minWidth: 220, long: true },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'updateTime', label: '更新时间', minWidth: 170 }
]
const formFields = [
  { prop: 'ruleName', label: '规则名称', required: true },
  { prop: 'ruleType', label: '规则类型', type: 'select', options: typeOptions, required: true },
  { prop: 'riskLevel', label: '风险级别', type: 'select', options: riskOptions, required: true },
  { prop: 'keywords', label: '匹配关键词', type: 'textarea', rows: 3, required: true },
  { prop: 'description', label: '规则说明', type: 'textarea', rows: 3 },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions, required: true }
]
</script>
