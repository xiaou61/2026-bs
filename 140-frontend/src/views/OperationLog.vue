<template>
  <DataPage title="操作日志" description="查看电子合同模板维护、审批、签署、归档等关键动作的日志记录" :api="api" :columns="columns" :form-fields="formFields" :row-actions="[]" :defaults="defaults" readonly />
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
  { prop: 'createdTime', label: '操作时间', width: 180 },
  { prop: 'status', label: '结果状态' }
]
const formFields = [
  { prop: 'operatorName', label: '操作人' },
  { prop: 'moduleName', label: '模块名称' },
  { prop: 'actionType', label: '动作类型' },
  { prop: 'targetName', label: '操作对象' },
  { prop: 'detailInfo', label: '操作详情', type: 'textarea' },
  { prop: 'status', label: '结果状态', type: 'select', options: [{ label: '成功', value: 'SUCCESS' }, { label: '处理中', value: 'PROCESSING' }, { label: '失败', value: 'DISABLED' }] }
]
const defaults = { status: 'SUCCESS' }
</script>
