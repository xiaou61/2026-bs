<template>
  <DataPage
    title="操作日志"
    description="查看关键模块操作人、动作、对象和结果记录"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :defaults="defaults"
    readonly
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { getOperationLogPage } from '../api'

const api = { page: getOperationLogPage, add: async () => {}, update: async () => {}, delete: async () => {} }
const columns = [
  { prop: 'operatorName', label: '操作人' },
  { prop: 'moduleName', label: '模块名称' },
  { prop: 'actionType', label: '动作类型' },
  { prop: 'targetName', label: '操作对象' },
  { prop: 'detailInfo', label: '详情', width: 200 },
  { prop: 'status', label: '结果' }
]
const formFields = []
const defaults = {}
</script>
