<template>
  <DataPage title="权限组" description="维护文档访问授权使用的企业权限组。" :api="api" :filters="filters" :columns="columns" :form-fields="formFields" :defaults="{ status: 1 }" />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { addGroup, deleteGroup, getGroupPage, updateGroup } from '../api'

const statusOptions = [
  { label: '启用', value: 1 },
  { label: '停用', value: 0 }
]
const statusMap = {
  1: { label: '启用', type: 'success' },
  0: { label: '停用', type: 'danger' }
}
const api = { page: getGroupPage, add: addGroup, update: updateGroup, delete: deleteGroup }
const filters = [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '组名/负责人' },
  { prop: 'status', label: '状态', type: 'select', options: statusOptions }
]
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'name', label: '权限组名称', minWidth: 170 },
  { prop: 'ownerName', label: '负责人' },
  { prop: 'description', label: '说明', minWidth: 260, long: true },
  { prop: 'status', label: '状态', map: statusMap }
]
const formFields = [
  { prop: 'name', label: '权限组名称', required: true },
  { prop: 'ownerName', label: '负责人' },
  { prop: 'description', label: '说明', type: 'textarea' },
  { prop: 'status', label: '启用状态', type: 'switch' }
]
</script>
