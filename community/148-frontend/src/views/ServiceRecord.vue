<template>
  <DataPage
    title="服务记录"
    description="维护记录编号、老人姓名、服务结论、服务时间和服务人员，支撑服务闭环留痕"
    :api="api"
    :columns="columns"
    :form-fields="formFields"
    :row-actions="rowActions"
    :defaults="defaults"
    :can-create="canManage"
    :can-edit="canManage"
    :can-delete="canManage"
    @row-action="handleAction"
  />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { useUserStore } from '../store/user'
import { getServiceRecordPage, addServiceRecord, updateServiceRecord, deleteServiceRecord, processServiceRecord, finishServiceRecord } from '../api'

const api = { page: getServiceRecordPage, add: addServiceRecord, update: updateServiceRecord, delete: deleteServiceRecord }
const userStore = useUserStore()
const role = computed(() => userStore.user?.role || '')
const canManage = computed(() => ['ADMIN', 'CAREGIVER'].includes(role.value))
const canProcess = computed(() => ['ADMIN', 'CONSULTANT'].includes(role.value))
const columns = [
  { prop: 'recordNo', label: '记录编号' },
  { prop: 'elderName', label: '老人姓名', width: 140 },
  { prop: 'serviceConclusion', label: '服务结论', width: 220 },
  { prop: 'serviceTime', label: '服务时间', width: 160 },
  { prop: 'caregiverName', label: '服务人员', width: 140 },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'recordNo', label: '记录编号' },
  { prop: 'elderName', label: '老人姓名' },
  { prop: 'serviceConclusion', label: '服务结论', type: 'textarea' },
  { prop: 'serviceTime', label: '服务时间' },
  { prop: 'caregiverName', label: '服务人员' },
  { prop: 'status', label: '状态', type: 'select', options: [{ label: '待处理', value: 'OPEN' }, { label: '处理中', value: 'PROCESSING' }, { label: '已完成', value: 'FINISHED' }] }
]
const rowActions = computed(() => canProcess.value ? [
  { command: 'process', label: '跟进', type: 'primary' },
  { command: 'finish', label: '结单', type: 'success' }
] : [])
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processServiceRecord(row.id)
  if (command === 'finish') await finishServiceRecord(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
