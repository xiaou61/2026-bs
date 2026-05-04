<template>
  <DataPage
    title="Prompt 资产"
    description="管理提示词标题、分类、团队、场景、标签和使用说明。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1 }"
  />
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import DataPage from '../components/DataPage.vue'
import { addAsset, deleteAsset, getAssetPage, getCategoryPage, getTeamPage, updateAsset } from '../api'

const categories = ref([])
const teams = ref([])
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '停用', type: 'danger' }
}
const api = { page: getAssetPage, add: addAsset, update: updateAsset, delete: deleteAsset }
const categoryOptions = computed(() => categories.value.map((item) => ({ label: item.name, value: item.id })))
const teamOptions = computed(() => teams.value.map((item) => ({ label: item.name, value: item.id })))
const filters = computed(() => [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '标题/场景/标签' },
  { prop: 'categoryId', label: '分类', type: 'select', options: categoryOptions.value },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'title', label: '资产标题', minWidth: 180 },
  { prop: 'categoryId', label: '分类ID' },
  { prop: 'teamId', label: '团队ID' },
  { prop: 'scene', label: '场景' },
  { prop: 'tags', label: '标签', minWidth: 160 },
  { prop: 'description', label: '说明', minWidth: 240, long: true },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = computed(() => [
  { prop: 'title', label: '资产标题', required: true },
  { prop: 'categoryId', label: '分类', type: 'select', required: true, options: categoryOptions.value },
  { prop: 'teamId', label: '团队', type: 'select', required: true, options: teamOptions.value },
  { prop: 'scene', label: '使用场景' },
  { prop: 'tags', label: '标签' },
  { prop: 'description', label: '说明', type: 'textarea' },
  { prop: 'status', label: '启用状态', type: 'switch' }
])

const loadOptions = async () => {
  const [categoryRes, teamRes] = await Promise.all([
    getCategoryPage({ pageNum: 1, pageSize: 100, status: 1 }),
    getTeamPage({ pageNum: 1, pageSize: 100, status: 1 })
  ])
  categories.value = categoryRes.data.records || []
  teams.value = teamRes.data.records || []
}

onMounted(loadOptions)
</script>
