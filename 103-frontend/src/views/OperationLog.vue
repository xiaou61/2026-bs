<template>
  <DataPage title="操作日志" description="查看客服质检系统关键操作记录。" :api="api" :filters="filters" :columns="columns" readonly />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { getLogPage } from '../api'
const api = { page: getLogPage }
const roleMap = { ADMIN: { label: '管理员', type: 'danger' }, SUPERVISOR: { label: '主管', type: 'success' }, AGENT: { label: '坐席', type: 'primary' }, QA: { label: '质检员', type: 'warning' }, SYSTEM: { label: '系统', type: 'info' } }
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'input', prop: 'moduleName', label: '模块' }]
const columns = [{ prop: 'username', label: '用户' }, { prop: 'role', label: '角色', map: roleMap }, { prop: 'moduleName', label: '模块' }, { prop: 'actionType', label: '动作' }, { prop: 'description', label: '描述', minWidth: 260, long: true }, { prop: 'createTime', label: '时间', minWidth: 170 }]
</script>
