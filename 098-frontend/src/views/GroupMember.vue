<template>
  <DataPage title="组成员" description="为权限组绑定企业用户，并维护成员角色和状态。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :defaults="{ status: 1, memberRole: '访问者' }" />
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import DataPage from '../components/DataPage.vue'
import { addMember, deleteMember, getGroupPage, getMemberPage, getUserPage, updateMember } from '../api'

const groups = ref([])
const users = ref([])
const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const roleOptions = [
  { label: '维护者', value: '维护者' },
  { label: '访问者', value: '访问者' }
]
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '停用', type: 'danger' }
}
const api = { page: getMemberPage, add: addMember, update: updateMember, delete: deleteMember }
const groupOptions = computed(() => groups.value.map((item) => ({ label: item.name, value: item.id })))
const userOptions = computed(() => users.value.map((item) => ({ label: `${item.nickname || item.username}`, value: item.id })))
const filters = computed(() => [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '成员角色' },
  { prop: 'groupId', label: '权限组', type: 'select', options: groupOptions.value },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
])
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'groupId', label: '权限组ID' },
  { prop: 'userId', label: '用户ID' },
  { prop: 'memberRole', label: '成员角色' },
  { prop: 'status', label: '状态', map: statusMap },
  { prop: 'createTime', label: '加入时间', minWidth: 180 }
]
const formFields = computed(() => [
  { prop: 'groupId', label: '权限组', type: 'select', required: true, options: groupOptions.value },
  { prop: 'userId', label: '用户', type: 'select', required: true, options: userOptions.value },
  { prop: 'memberRole', label: '成员角色', type: 'select', options: roleOptions },
  { prop: 'status', label: '启用状态', type: 'switch' }
])

const loadOptions = async () => {
  const [groupRes, userRes] = await Promise.all([
    getGroupPage({ pageNum: 1, pageSize: 100, status: 1 }),
    getUserPage({ pageNum: 1, pageSize: 100, status: 1 })
  ])
  groups.value = groupRes.data.list || []
  users.value = userRes.data.list || []
}

onMounted(loadOptions)
</script>
