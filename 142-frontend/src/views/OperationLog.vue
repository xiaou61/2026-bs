<template>
  <DataPage title="操作日志" description="记录保险保单、理赔申请、材料审核和赔付管理等关键模块的操作留痕，便于巡检和审计追踪" :api="api" :columns="columns" :form-fields="formFields" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" />
</template>

<script setup>
import { computed } from 'vue'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getOperationLogPage, addOperationLog, updateOperationLog, deleteOperationLog } from '../api'

const api = { page: getOperationLogPage, add: addOperationLog, update: updateOperationLog, delete: deleteOperationLog }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => role.value === 'ADMIN')
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
