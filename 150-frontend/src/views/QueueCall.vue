<template>
  <DataPage title="队列叫号" description="叫号编号、患者姓名、叫号时间、候诊状态、诊室编号和状态维护" :api="api" :columns="columns" :form-fields="formFields" :row-actions="rowActions" :defaults="defaults" :can-create="canManage" :can-edit="canManage" :can-delete="canManage" @row-action="handleAction" />
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'
import DataPage from '../components/DataPage.vue'
import { addQueueCall, deleteQueueCall, finishQueueCall, getQueueCallPage, processQueueCall, updateQueueCall } from '../api'
import { useUserStore } from '../store/user'

const userStore = useUserStore()
const canManage = computed(() => ['ADMIN', 'TECHNICIAN'].includes(userStore.user?.role))
const rowActions = computed(() => canManage.value
  ? [
      { command: 'process', label: '叫号中', type: 'warning' },
      { command: 'finish', label: '完成就诊', type: 'success' }
    ]
  : [])
const api = { page: getQueueCallPage, add: addQueueCall, update: updateQueueCall, delete: deleteQueueCall }
const columns = [
  { prop: 'queueNo', label: '叫号编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'callTime', label: '叫号时间' },
  { prop: 'waitingStatus', label: '候诊状态' },
  { prop: 'roomNo', label: '诊室编号' },
  { prop: 'status', label: '状态' }
]
const formFields = [
  { prop: 'queueNo', label: '叫号编号' },
  { prop: 'patientName', label: '患者姓名' },
  { prop: 'callTime', label: '叫号时间' },
  { prop: 'waitingStatus', label: '候诊状态' },
  { prop: 'roomNo', label: '诊室编号' },
  {
    prop: 'status',
    label: '状态',
    type: 'select',
    options: [
      { label: '待处理', value: 'OPEN' },
      { label: '处理中', value: 'PROCESSING' },
      { label: '已完成', value: 'FINISHED' }
    ]
  }
]
const defaults = { status: 'OPEN' }

const handleAction = async ({ command, row, refresh }) => {
  if (command === 'process') await processQueueCall(row.id)
  if (command === 'finish') await finishQueueCall(row.id)
  ElMessage.success('操作成功')
  refresh()
}
</script>
