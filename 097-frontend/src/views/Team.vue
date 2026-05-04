<template>
  <DataPage
    title="团队空间"
    description="维护 Prompt 协作团队、负责人、成员数量和启用状态。"
    :api="api"
    :filters="filters"
    :columns="columns"
    :form-fields="formFields"
    :defaults="{ status: 1, memberCount: 0 }"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addTeam, deleteTeam, getTeamPage, updateTeam } from '../api'

const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '停用', type: 'danger' }
}
const api = { page: getTeamPage, add: addTeam, update: updateTeam, delete: deleteTeam }
const filters = [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '团队或负责人' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'name', label: '团队名称', minWidth: 170 },
  { prop: 'ownerName', label: '负责人' },
  { prop: 'memberCount', label: '成员数' },
  { prop: 'description', label: '说明', minWidth: 220, long: true },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'name', label: '团队名称', required: true },
  { prop: 'ownerName', label: '负责人' },
  { prop: 'memberCount', label: '成员数量', type: 'number', min: 0 },
  { prop: 'description', label: '说明', type: 'textarea' },
  { prop: 'status', label: '启用状态', type: 'switch' }
]
</script>
