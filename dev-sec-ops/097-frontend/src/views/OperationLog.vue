<template>
  <DataPage
    title="操作日志"
    description="查看系统关键操作、模块、动作和操作者。"
    :api="{ page: getLogPage }"
    :filters="filters"
    :columns="columns"
    readonly
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { getLogPage } from '../api'

const filters = [
  { prop: 'keyword', label: '关键词', type: 'input', placeholder: '用户或描述' },
  { prop: 'moduleName', label: '模块', type: 'input', placeholder: '模块名称' }
]
const columns = [
  { prop: 'id', label: 'ID', width: 80 },
  { prop: 'username', label: '用户' },
  { prop: 'role', label: '角色' },
  { prop: 'moduleName', label: '模块' },
  { prop: 'actionType', label: '动作' },
  { prop: 'description', label: '描述', minWidth: 260, long: true },
  { prop: 'createTime', label: '时间', minWidth: 180 }
]
</script>
