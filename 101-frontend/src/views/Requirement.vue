<template>
  <DataPage
    ref="pageRef"
    title="岗位要求"
    description="按岗位配置技能、学历、经验和证书要求权重。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, requirementType: '技能', weight: 1 }"
    
  />
</template>

<script setup>
import { ref } from 'vue'
import DataPage from '../components/DataPage.vue'
import { addRequirement, deleteRequirement, getRequirementPage, updateRequirement } from '../api'

const pageRef = ref()

const statusMap = { 0: { label: '停用', type: 'info' }, 1: { label: '启用', type: 'success' } }
const api = { page: getRequirementPage, add: addRequirement, update: updateRequirement, delete: deleteRequirement }
const typeOptions = [{ label: '技能', value: '技能' }, { label: '学历', value: '学历' }, { label: '经验', value: '经验' }, { label: '证书', value: '证书' }]
const statusOptions = [{ label: '启用', value: 1 }, { label: '停用', value: 0 }]

const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'input', prop: 'jobId', label: '岗位ID' }, { type: 'select', prop: 'status', label: '状态', options: statusOptions }]
const columns = [{ prop: 'jobId', label: '岗位ID' }, { prop: 'requirementType', label: '类型' }, { prop: 'keyword', label: '关键词' }, { prop: 'weight', label: '权重' }, { prop: 'description', label: '说明', minWidth: 220, long: true }, { prop: 'status', label: '状态', map: statusMap }]
const formFields = [{ prop: 'jobId', label: '岗位ID', type: 'number', min: 1, required: true }, { prop: 'requirementType', label: '类型', type: 'select', options: typeOptions }, { prop: 'keyword', label: '关键词', required: true }, { prop: 'weight', label: '权重', type: 'number', min: 0, max: 5, precision: 2 }, { prop: 'description', label: '说明', type: 'textarea', rows: 3 }, { prop: 'status', label: '状态', type: 'select', options: statusOptions }]
</script>
