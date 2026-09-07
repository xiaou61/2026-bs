<template>
  <DataPage
    ref="pageRef"
    title="候选人档案"
    description="维护候选人学历、经验、目标岗位、技能标签和期望薪资。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, education: '本科' }"
    
  />
</template>

<script setup>
import { computed, ref } from 'vue'
import DataPage from '../components/DataPage.vue'
import { addCandidate, deleteCandidate, getCandidatePage, updateCandidate } from '../api'
import { useUserStore } from '../store/user'

const pageRef = ref()
const userStore = useUserStore()
const isCandidate = computed(() => userStore.user?.role === 'CANDIDATE')

const statusMap = { 0: { label: '停用', type: 'info' }, 1: { label: '启用', type: 'success' } }
const api = { page: getCandidatePage, add: addCandidate, update: updateCandidate, delete: deleteCandidate }
const statusOptions = [{ label: '启用', value: 1 }, { label: '停用', value: 0 }]

const filters = [{ type: 'input', prop: 'keyword', label: '关键词', placeholder: '姓名/技能' }, { type: 'input', prop: 'targetJob', label: '目标岗位' }, { type: 'select', prop: 'status', label: '状态', options: statusOptions }]
const columns = [{ prop: 'realName', label: '姓名' }, { prop: 'education', label: '学历' }, { prop: 'workYears', label: '经验年限' }, { prop: 'targetJob', label: '目标岗位', minWidth: 160 }, { prop: 'skillTags', label: '技能标签', minWidth: 220, long: true }, { prop: 'expectedSalary', label: '期望薪资' }, { prop: 'status', label: '状态', map: statusMap }]
const formFields = computed(() => {
  const fields = []
  if (!isCandidate.value) fields.push({ prop: 'userId', label: '用户ID', type: 'number', min: 1 })
  fields.push(
    { prop: 'realName', label: '姓名', required: true },
    { prop: 'gender', label: '性别' },
    { prop: 'education', label: '学历' },
    { prop: 'workYears', label: '经验年限', type: 'number', min: 0 },
    { prop: 'targetJob', label: '目标岗位' },
    { prop: 'skillTags', label: '技能标签', type: 'textarea', rows: 3 },
    { prop: 'expectedSalary', label: '期望薪资', type: 'number', min: 0, precision: 2 }
  )
  if (!isCandidate.value) fields.push({ prop: 'status', label: '状态', type: 'select', options: statusOptions })
  return fields
})
</script>
