<template>
  <DataPage title="操作日志" description="关键模块操作人、动作、对象、详情和结果记录" :api="api" :columns="columns" :form-fields="formFields" :row-actions="[]" :defaults="defaults" :readonly="true" />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { getOperationLogPage, addOperationLog, updateOperationLog, deleteOperationLog } from '../api'

const api = { page: getOperationLogPage, add: addOperationLog, update: updateOperationLog, delete: deleteOperationLog }
const columns = [
  { prop: 'operatorName', label: '操作人' },
  { prop: 'actionType', label: '动作' },
  { prop: 'moduleName', label: '所属模块' },
  { prop: 'targetName', label: '操作对象' },
  { prop: 'detailInfo', label: '详情' },
  { prop: 'status', label: '结果' }
]
const formFields = [
  { prop: 'operatorName', label: '操作人' },
  { prop: 'actionType', label: '动作' },
  { prop: 'moduleName', label: '所属模块' },
  { prop: 'targetName', label: '操作对象' },
  { prop: 'detailInfo', label: '详情', type: 'textarea' },
  { prop: 'status', label: '结果', type: 'select', options: [{ label: '成功', value: 'SUCCESS' }] }
]
const defaults = { status: 'SUCCESS' }
</script>
