<template>
  <DataPage title="操作日志" description="查看用户在招聘匹配系统中的关键操作记录。" :api="api" :filters="filters" :columns="columns" readonly />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { getLogPage } from '../api'

const api = { page: getLogPage }
const roleMap = { ADMIN: { label: '管理员', type: 'danger' }, HR: { label: 'HR', type: 'success' }, CANDIDATE: { label: '候选人', type: 'warning' }, INTERVIEWER: { label: '面试官', type: 'primary' } }
const moduleOptions = [
  { label: '认证', value: '认证' },
  { label: '用户管理', value: '用户管理' },
  { label: '候选人档案', value: '候选人档案' },
  { label: '简历材料', value: '简历材料' },
  { label: '证书材料', value: '证书材料' },
  { label: '岗位管理', value: '岗位管理' },
  { label: '岗位要求', value: '岗位要求' },
  { label: '解析任务', value: '解析任务' },
  { label: '解析结果', value: '解析结果' },
  { label: '匹配任务', value: '匹配任务' },
  { label: '匹配结果', value: '匹配结果' },
  { label: '面试排期', value: '面试排期' },
  { label: '面试反馈', value: '面试反馈' }
]
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'select', prop: 'moduleName', label: '模块', options: moduleOptions }]
const columns = [{ prop: 'username', label: '用户' }, { prop: 'role', label: '角色', map: roleMap }, { prop: 'moduleName', label: '模块' }, { prop: 'actionType', label: '动作' }, { prop: 'description', label: '描述', minWidth: 260, long: true }, { prop: 'createTime', label: '时间', minWidth: 170 }]
</script>
