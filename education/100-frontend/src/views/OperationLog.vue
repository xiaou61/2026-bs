<template>
  <DataPage
    title="操作日志"
    description="查看登录、课程、提交、检测、预警、整改和申诉操作记录。"
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
  TEACHER: { label: '教师', type: 'success' },
  STUDENT: { label: '学生', type: 'warning' },
  REVIEWER: { label: '复核员', type: 'primary' }
}
const filters = [
  { type: 'input', prop: 'keyword', label: '关键词', placeholder: '用户/描述' },
  { type: 'select', prop: 'moduleName', label: '模块', options: [
    { label: '认证', value: '认证' },
    { label: '课程管理', value: '课程管理' },
    { label: '文本提交', value: '文本提交' },
    { label: '检测任务', value: '检测任务' },
    { label: '检测结果', value: '检测结果' },
    { label: '诚信预警', value: '诚信预警' },
    { label: '整改跟踪', value: '整改跟踪' },
    { label: '申诉管理', value: '申诉管理' }
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
