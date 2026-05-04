<template>
  <DataPage title="文档授权" description="为知识空间或单篇文档绑定权限组和访问级别。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :defaults="{ status: 1, permissionLevel: 'READ' }" />
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import DataPage from '../components/DataPage.vue'
import { addPermission, deletePermission, getDocumentPage, getGroupPage, getPermissionPage, getSpacePage, updatePermission } from '../api'

const spaces = ref([])
const documents = ref([])
const groups = ref([])
const levelOptions = [
  { label: '只读', value: 'READ' },
  { label: '问答', value: 'QA' },
  { label: '管理', value: 'MANAGE' }
]
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const levelMap = {
  READ: { label: '只读', type: 'info' },
  QA: { label: '问答', type: 'success' },
  MANAGE: { label: '管理', type: 'warning' }
}
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '停用', type: 'danger' }
}
const api = { page: getPermissionPage, add: addPermission, update: updatePermission, delete: deletePermission }
const spaceOptions = computed(() => spaces.value.map((item) => ({ label: item.name, value: item.id })))
const documentOptions = computed(() => documents.value.map((item) => ({ label: item.title, value: item.id })))
const groupOptions = computed(() => groups.value.map((item) => ({ label: item.name, value: item.id })))
const filters = computed(() => [
  { prop: 'spaceId', label: '空间', type: 'select', options: spaceOptions.value },
  { prop: 'documentId', label: '文档', type: 'select', options: documentOptions.value },
  { prop: 'groupId', label: '权限组', type: 'select', options: groupOptions.value },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'spaceId', label: '空间ID' },
  { prop: 'documentId', label: '文档ID' },
  { prop: 'groupId', label: '权限组ID' },
  { prop: 'permissionLevel', label: '授权级别', map: levelMap },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = computed(() => [
  { prop: 'spaceId', label: '知识空间', type: 'select', options: spaceOptions.value },
  { prop: 'documentId', label: '知识文档', type: 'select', options: documentOptions.value },
  { prop: 'groupId', label: '权限组', type: 'select', required: true, options: groupOptions.value },
  { prop: 'permissionLevel', label: '授权级别', type: 'select', options: levelOptions },
  { prop: 'status', label: '启用状态', type: 'switch' }
])

const loadOptions = async () => {
  const [spaceRes, documentRes, groupRes] = await Promise.all([
    getSpacePage({ pageNum: 1, pageSize: 100, status: 1 }),
    getDocumentPage({ pageNum: 1, pageSize: 100, status: 1 }),
    getGroupPage({ pageNum: 1, pageSize: 100, status: 1 })
  ])
  spaces.value = spaceRes.data.list || []
  documents.value = documentRes.data.list || []
  groups.value = groupRes.data.list || []
}

onMounted(loadOptions)
</script>
