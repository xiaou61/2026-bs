<template>
  <DataPage title="操作日志" description="记录服务项目、服务预约、志愿签到、互助兑换和通知发布等关键模块的操作留痕，便于站点巡检与审计追踪" :api="api" :columns="columns" :form-fields="formFields" :defaults="defaults" :can-create="false" :can-edit="false" :can-delete="false" />
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
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '成功', value: 'SUCCESS' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const defaults = { status: 'SUCCESS' }
</script>
