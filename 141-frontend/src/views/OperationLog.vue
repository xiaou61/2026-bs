<template>
  <DataPage title="操作日志" description="查看资产档案、借用归还、维修折旧和预警通知等关键操作日志" :api="api" :columns="columns" :form-fields="formFields" :row-actions="[]" :defaults="defaults" readonly />
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
