<template>
  <DataPage
    ref="pageRef"
    title="简历材料"
    description="管理简历文件、简历文本、学历、技能和解析状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ parseStatus: 0, education: '本科' }"
    
  />
</template>

<script setup>
import { computed, ref } from 'vue'
import DataPage from '../components/DataPage.vue'
import { addResume, deleteResume, getResumePage, updateResume } from '../api'
import { useUserStore } from '../store/user'

const pageRef = ref()
const userStore = useUserStore()
const isCandidate = computed(() => userStore.user?.role === 'CANDIDATE')

const parseMap = { 0: { label: '待解析', type: 'warning' }, 1: { label: '已解析', type: 'success' } }
const api = { page: getResumePage, add: addResume, update: updateResume, delete: deleteResume }
const parseOptions = [{ label: '待解析', value: 0 }, { label: '已解析', value: 1 }]

const filters = computed(() => {
  const items = [{ type: 'input', prop: 'keyword', label: '关键词', placeholder: '文件名/技能' }]
  if (!isCandidate.value) items.push({ type: 'input', prop: 'candidateId', label: '候选人ID' })
  items.push({ type: 'select', prop: 'parseStatus', label: '解析状态', options: parseOptions })
  return items
})
const columns = computed(() => {
  const items = [{ prop: 'fileName', label: '文件名', minWidth: 180 }, { prop: 'education', label: '学历' }, { prop: 'skills', label: '技能', minWidth: 220, long: true }, { prop: 'workYears', label: '经验' }, { prop: 'parseStatus', label: '状态', map: parseMap }]
  if (!isCandidate.value) items.splice(1, 0, { prop: 'candidateId', label: '候选人ID' })
  return items
})
const formFields = computed(() => {
  const fields = []
  if (!isCandidate.value) fields.push({ prop: 'candidateId', label: '候选人ID', type: 'number', min: 1, required: true })
  fields.push(
    { prop: 'fileName', label: '文件名', required: true },
    { prop: 'fileUrl', label: '文件地址' },
    { prop: 'resumeText', label: '简历文本', type: 'textarea', rows: 5 },
    { prop: 'education', label: '学历' },
    { prop: 'skills', label: '技能', type: 'textarea', rows: 3 },
    { prop: 'workYears', label: '经验年限', type: 'number', min: 0 }
  )
  if (!isCandidate.value) fields.push({ prop: 'parseStatus', label: '解析状态', type: 'select', options: parseOptions })
  return fields
})
</script>
