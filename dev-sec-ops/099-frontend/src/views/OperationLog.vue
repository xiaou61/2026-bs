<template>
  <DataPage
    title="操作日志"
    description="查看用户在认证、作品、审核、登记、存证和申诉模块中的操作记录。"
    :api="api"
    :filters="filters"
    :columns="columns"
    readonly
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { getLogPage } from '../api'

const api = { page: getLogPage }
const roleMap = {
  ADMIN: { label: '管理员', type: 'danger' },
  AUDITOR: { label: '审核员', type: 'success' },
  CREATOR: { label: '创作者', type: 'warning' }
}
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '用户/描述' },
  { type: 'select', prop: 'moduleName', label: '模块', options: [
    { label: '认证', value: '认证' },
    { label: '图片作品', value: '图片作品' },
    { label: '审核任务', value: '审核任务' },
    { label: '版权登记', value: '版权登记' },
    { label: '版权存证', value: '版权存证' },
    { label: '申诉处理', value: '申诉处理' }
  ] }
]
const columns = [
  { prop: 'username', label: '用户' },
  { prop: 'role', label: '角色', map: roleMap },
  { prop: 'moduleName', label: '模块' },
  { prop: 'actionType', label: '动作' },
  { prop: 'description', label: '描述', minWidth: 260, long: true },
  { prop: 'createTime', label: '时间', minWidth: 170 }
]
</script>
