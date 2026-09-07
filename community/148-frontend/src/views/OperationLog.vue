<template>
  <DataPage
    title="操作日志"
    description="查看关键模块的操作人、动作、对象、详情和执行结果，支撑社区养老服务留痕"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :defaults="defaults"
    :readonly="true"
  />
</template>

<script setup>
import DataPage from '../components/DataPage.vue'
import { getOperationLogPage, addOperationLog, updateOperationLog, deleteOperationLog } from '../api'

const api = { page: getOperationLogPage, add: addOperationLog, update: updateOperationLog, delete: deleteOperationLog }
const columns = [
  { prop: 'operatorName', label: '操作人' },
  { prop: 'moduleName', label: '模块名称' },
  { prop: 'actionType', label: '动作类型' },
  { prop: 'targetName', label: '操作对象' },
  { prop: 'detailInfo', label: '操作详情', width: 220 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'operatorName', label: '操作人' },
  { prop: 'moduleName', label: '模块名称' },
  { prop: 'actionType', label: '动作类型' },
  { prop: 'targetName', label: '操作对象' },
  { prop: 'detailInfo', label: '操作详情', type: 'textarea' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '成功', value: 'SUCCESS' }, { label: '处理中', value: 'PROCESSING' }] }
]
const defaults = { status: 'SUCCESS' }
</script>
