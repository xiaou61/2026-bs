<template>
  <DataPage
    ref="pageRef"
    title="问答会话"
    description="创建员工问答会话，选择知识空间并维护会话状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 0 }"
    :row-actions="rowActions"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addSession, closeSession, deleteSession, getSessionPage, getSpacePage, updateSession } from '../api'

const pageRef = ref()
const spaces = ref([])
const statusOptions = [
  { label: '进行中', value: 0 },
  { label: '已关闭', value: 1 }
]
const statusMap = {
  0: { label: '进行中', type: 'success' },
  1: { label: '已关闭', type: 'info' }
}
const api = { page: getSessionPage, add: addSession, update: updateSession, delete: deleteSession }
const spaceOptions = computed(() => spaces.value.map((item) => ({ label: item.name, value: item.id })))
const filters = computed(() => [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '会话编号/标题' },
  { prop: 'spaceId', label: '空间', type: 'select', options: spaceOptions.value },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'sessionNo', label: '会话编号', minWidth: 180 },
  { prop: 'title', label: '会话标题', minWidth: 180 },
  { prop: 'spaceId', label: '空间ID' },
  { prop: 'userId', label: '用户ID' },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'createTime', label: '创建时间', minWidth: 180 }
]
const formFields = computed(() => [
  { prop: 'title', label: '会话标题', required: true },
  { prop: 'spaceId', label: '知识空间', type: 'select', required: true, options: spaceOptions.value },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
])
const rowActions = [{ name: 'close', label: '关闭', type: 'warning' }]

const handleAction = async (name, row) => {
  if (name === 'close') {
    await closeSession(row.id)
    ElMessage.success('会话已关闭')
    pageRef.value.loadData()
  }
}

const loadOptions = async () => {
  const res = await getSpacePage({ pageNum: 1, pageSize: 100, status: 1 })
  spaces.value = res.data.list || []
}

onMounted(loadOptions)
</script>
