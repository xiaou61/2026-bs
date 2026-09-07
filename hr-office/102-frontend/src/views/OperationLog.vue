<template>
  <DataPage title="操作日志" description="查看法律咨询系统中的关键操作留痕。" :api="api" :filters="filters" :columns="columns" readonly />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { getLogPage } from '../api'

const api = { page: getLogPage }
const roleMap = { ADMIN: { label: '管理员', type: 'danger' }, LAWYER: { label: '律师', type: 'success' }, ASSISTANT: { label: '助理', type: 'warning' }, CLIENT: { label: '委托人', type: 'primary' }, SYSTEM: { label: '系统', type: 'info' } }
const filters = [{ type: 'input', prop: 'keyword', label: '关键词' }, { type: 'select', prop: 'moduleName', label: '模块', options: [{ label: '案件台账', value: '案件台账' }, { label: '法律文书', value: '法律文书' }, { label: '证据材料', value: '证据材料' }, { label: '费用记录', value: '费用记录' }] }]
const columns = [{ prop: 'username', label: '用户' }, { prop: 'role', label: '角色', map: roleMap }, { prop: 'moduleName', label: '模块' }, { prop: 'actionType', label: '动作' }, { prop: 'description', label: '描述', minWidth: 260, long: true }, { prop: 'createTime', label: '时间', minWidth: 170 }]
</script>
